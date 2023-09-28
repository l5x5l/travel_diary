package com.strayalphaca.presentation.screens.diary.write

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.strayalphaca.travel_diary.diary.model.DiaryDetail
import com.strayalphaca.travel_diary.diary.model.DiaryModifyData
import com.strayalphaca.travel_diary.diary.model.DiaryWriteData
import com.strayalphaca.travel_diary.diary.model.Feeling
import com.strayalphaca.travel_diary.diary.model.Weather
import com.strayalphaca.travel_diary.diary.use_case.UseCaseGetDiaryDetail
import com.strayalphaca.travel_diary.diary.use_case.UseCaseModifyDiary
import com.strayalphaca.travel_diary.diary.use_case.UseCaseUploadDiary
import com.strayalphaca.domain.model.BaseResponse
import com.strayalphaca.presentation.screens.diary.model.CurrentShowSelectView
import com.strayalphaca.presentation.screens.diary.model.DiaryDate
import com.strayalphaca.presentation.screens.diary.model.MusicPlayer
import com.strayalphaca.presentation.utils.UriHandler
import com.strayalphaca.travel_diary.domain.file.usecase.UseCaseUploadFiles
import com.strayalphaca.travel_diary.map.model.City
import com.strayalphaca.travel_diary.map.model.Province
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiaryWriteViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val useCaseGetDiaryDetail: UseCaseGetDiaryDetail,
    private val useCaseUploadFiles: UseCaseUploadFiles,
    private val useCaseUploadDiary: UseCaseUploadDiary,
    private val useCaseModifyDiary: UseCaseModifyDiary,
    private val musicPlayer: MusicPlayer,
    private val uriHandler: UriHandler
) : ViewModel() {
    private val events = Channel<DiaryWriteEvent>()
    val state: StateFlow<DiaryWriteState> = events.receiveAsFlow()
        .runningFold(DiaryWriteState(), ::reduce)
        .stateIn(viewModelScope, SharingStarted.Eagerly, DiaryWriteState())

    private val _writingContent = MutableStateFlow("")
    val writingContent = _writingContent.asStateFlow()

    private val _goBackNavigationEvent = MutableSharedFlow<Boolean>()
    val goBackNavigationEvent = _goBackNavigationEvent.asSharedFlow()

    private val _musicProgress = MutableStateFlow(0f)
    val musicProgress = _musicProgress.asStateFlow()

    private var musicPlayerJob : Job? = null

    private var diaryId: String? = null

    init {
        musicPlayer.setCompleteCallback {
            viewModelScope.launch {
                events.send(DiaryWriteEvent.PauseMusic)
            }
        }

        savedStateHandle.get<String>("diary_date")?.let { dateString ->
            viewModelScope.launch {
                events.send(DiaryWriteEvent.SetDiaryDate(DiaryDate.getInstanceFromDateString(dateString)))
            }
        }
    }

    fun tryLoadDetail(id : String) {
        diaryId = id
        viewModelScope.launch {
            events.send(DiaryWriteEvent.DiaryLoading)

            val response = useCaseGetDiaryDetail(id)
            if (response is BaseResponse.Success) {
                inputContent(response.data.content)
                events.send(DiaryWriteEvent.DiaryLoadingSuccess(response.data))
            } else {
                events.send(DiaryWriteEvent.DiaryLoadingFail)
            }
        }
    }

    fun inputContent(content : String) {
        viewModelScope.launch {
            _writingContent.value = content
        }
    }

    fun inputVoiceFile(file : Uri, isLocal : Boolean = false) {
        viewModelScope.launch {
            musicPlayer.setMusic(file, isLocal)
            events.send(DiaryWriteEvent.AddVoiceFile(file))
        }
    }

    fun removeVoiceFile() {
        viewModelScope.launch {
            musicPlayer.remove()
            events.send(DiaryWriteEvent.RemoveVoiceFile)
        }
    }

    fun inputImageFile(file : List<Uri>) {
        viewModelScope.launch {
            events.send(DiaryWriteEvent.ChangeImageList(file))
        }
    }

    fun setFeeling(feeling: Feeling) {
        viewModelScope.launch {
            events.send(DiaryWriteEvent.SetFeeling(feeling = feeling))
        }
    }

    fun setWeather(weather : Weather) {
        viewModelScope.launch {
            events.send(DiaryWriteEvent.SetWeather(weather = weather))
        }
    }

    fun showSelectView(target : CurrentShowSelectView) {
        viewModelScope.launch {
            if (target == state.value.currentShowSelectView) {
                events.send(DiaryWriteEvent.HideSelectView)
                return@launch
            }

            if (target == CurrentShowSelectView.WEATHER) {
                events.send(DiaryWriteEvent.ShowSelectWeatherView)
            }

            if (target == CurrentShowSelectView.FEELING) {
                events.send(DiaryWriteEvent.ShowSelectFeelingView)
            }
        }
    }

    fun releaseMusicPlayer() {
        musicPlayer.release()
    }

    fun playMusic() {
        viewModelScope.launch {
            musicPlayer.playMusic()
            events.send(DiaryWriteEvent.PlayingMusic)
        }
    }

    fun pauseMusic() {
        viewModelScope.launch {
            musicPlayer.stopMusic()
            events.send(DiaryWriteEvent.PauseMusic)
        }
    }

    private fun startMusicPlayerJob() {
        musicPlayerJob = viewModelScope.launch {
            while (true) {

                if (state.value.musicPlaying) {
                    _musicProgress.value = musicPlayer.getProgress()
                }
                delay(250L)
            }
        }
    }

    fun dragMusicProgressByUser(progress : Float) {
        pauseMusic()
        musicPlayer.setPosition(progress)
        _musicProgress.value = progress
    }

    fun uploadDiary() {
        viewModelScope.launch {
            events.send(DiaryWriteEvent.DiaryWriteLoading)

            val voiceFile = state.value.voiceFile?.let { uriHandler.uriToFile(it) }
            val mediaFileList = state.value.imageFiles.map { uriHandler.uriToFile(it) }

            val voiceFileUploadJob = voiceFile?.let {
                async {
                    useCaseUploadFiles(listOf(it))
                }
            }
            val mediaFileListUploadJob = async {
                useCaseUploadFiles(mediaFileList)
            }

            val voiceFileLink = voiceFileUploadJob?.await()
            val mediaFileLinkList = mediaFileListUploadJob.await()

            if (
                !((voiceFileLink == null || voiceFileLink is BaseResponse.Success) && mediaFileLinkList is BaseResponse.Success)
            ) {
                events.send(DiaryWriteEvent.DiaryWriteFail)
                return@launch
            }

            val mediaFileIdList = mediaFileLinkList.data
            val voiceFileId = voiceFileLink?.let { response ->
                (response as BaseResponse.Success)
                if (response.data.isNotEmpty()) response.data[0] else null
            }

            val response = if (diaryId == null) {
                useCaseUploadDiary(
                    DiaryWriteData(
                        feeling = state.value.feeling,
                        weather = state.value.weather,
                        content = writingContent.value,
                        medias = mediaFileIdList,
                        voice = voiceFileId,
                        cityId = null,
                        recordDate = ""
                    )
                )
            } else {
                useCaseModifyDiary(
                    DiaryModifyData(
                        id = diaryId!!,
                        feeling = state.value.feeling,
                        weather = state.value.weather,
                        content = writingContent.value,
                        medias = mediaFileIdList,
                        voice = voiceFileId,
                        cityId = null,
                        date = ""
                    )
                )
            }

            if (response is BaseResponse.EmptySuccess) {
                events.send(DiaryWriteEvent.DiaryWriteSuccess)
            } else {
                events.send(DiaryWriteEvent.DiaryWriteFail)
            }
        }
    }

    fun showLocationPickerDialog() {
        viewModelScope.launch {
            events.send(DiaryWriteEvent.SetShowLocationPickerDialog(true))
        }
    }

    fun hideLocationPickerDialog() {
        viewModelScope.launch {
            events.send(DiaryWriteEvent.SetShowLocationPickerDialog(false))
        }
    }

    fun selectCityById(cityId : Int) {
        viewModelScope.launch {
            events.send(DiaryWriteEvent.SelectLocationById(cityId))
        }
    }

    private fun callGoBackNavigationEvent() {
        viewModelScope.launch {
            _goBackNavigationEvent.emit(true)
        }
    }

    private fun reduce(state: DiaryWriteState, events: DiaryWriteEvent): DiaryWriteState {
        return when (events) {
            DiaryWriteEvent.DiaryLoading -> {
                state.copy(buttonActive = false, showLoadingError = false, showInitLoading = true)
            }
            DiaryWriteEvent.DiaryLoadingFail -> {
                state.copy(buttonActive = true, showLoadingError = true, showInitLoading = false)
            }
            is DiaryWriteEvent.DiaryLoadingSuccess -> {
                state.copy(
                    buttonActive = true,
                    showLoadingError = false,
                    feeling = events.diaryDetail.feeling,
                    weather = events.diaryDetail.weather,
                    showInitLoading = false
                )
            }
            DiaryWriteEvent.DiaryWriteLoading -> {
                state.copy(buttonActive = false)
            }
            DiaryWriteEvent.DiaryWriteFail -> {
                state.copy(buttonActive = true)
            }
            DiaryWriteEvent.DiaryWriteSuccess -> {
                callGoBackNavigationEvent()
                state.copy(buttonActive = true)
            }
            is DiaryWriteEvent.AddVoiceFile -> {
                state.copy(voiceFile = events.file)
            }
            DiaryWriteEvent.RemoveVoiceFile -> {
                state.copy(voiceFile = null)
            }
            is DiaryWriteEvent.ChangeImageList -> {
                state.copy(imageFiles = events.file)
            }
            is DiaryWriteEvent.SetFeeling -> {
                state.copy(feeling = events.feeling, currentShowSelectView = null)
            }
            is DiaryWriteEvent.SetWeather -> {
                state.copy(weather = events.weather, currentShowSelectView = null)
            }
            DiaryWriteEvent.HideSelectView -> {
                state.copy(currentShowSelectView = null)
            }
            DiaryWriteEvent.ShowSelectFeelingView -> {
                state.copy(currentShowSelectView = CurrentShowSelectView.FEELING)
            }
            DiaryWriteEvent.ShowSelectWeatherView -> {
                state.copy(currentShowSelectView = CurrentShowSelectView.WEATHER)
            }
            DiaryWriteEvent.PlayingMusic -> {
                startMusicPlayerJob()
                state.copy(musicPlaying = true)
            }
            DiaryWriteEvent.PauseMusic -> {
                musicPlayerJob?.cancel()
                state.copy(musicPlaying = false)
            }
            is DiaryWriteEvent.SetShowLocationPickerDialog -> {
                state.copy(showLocationPickerDialog = events.show)
            }
            is DiaryWriteEvent.SelectLocationById -> {
                val city = City.findCity(events.cityId)
                val province = Province.findProvince(city.provinceId)
                state.copy(cityName = "${province.name} ${city.name}", cityId = city.id)
            }
            is DiaryWriteEvent.SetDiaryDate -> {
                state.copy(
                    diaryDate = events.diaryDate
                )
            }
        }
    }

}

sealed class DiaryWriteEvent {
    object DiaryLoading : DiaryWriteEvent()
    object DiaryLoadingFail : DiaryWriteEvent()
    class DiaryLoadingSuccess(val diaryDetail: DiaryDetail) : DiaryWriteEvent()
    object DiaryWriteLoading : DiaryWriteEvent()
    object DiaryWriteFail : DiaryWriteEvent()
    object DiaryWriteSuccess : DiaryWriteEvent()
    class AddVoiceFile(val file : Uri) : DiaryWriteEvent()
    object RemoveVoiceFile : DiaryWriteEvent()
    class ChangeImageList(val file : List<Uri>) : DiaryWriteEvent()
    class SetFeeling(val feeling: Feeling) : DiaryWriteEvent()
    class SetWeather(val weather: Weather) : DiaryWriteEvent()
    object ShowSelectFeelingView : DiaryWriteEvent()
    object ShowSelectWeatherView : DiaryWriteEvent()
    object HideSelectView : DiaryWriteEvent()
    object PlayingMusic : DiaryWriteEvent()
    object PauseMusic : DiaryWriteEvent()
    class SetShowLocationPickerDialog(val show : Boolean) : DiaryWriteEvent()
    class SelectLocationById(val cityId : Int) : DiaryWriteEvent()
    class SetDiaryDate(val diaryDate: DiaryDate) : DiaryWriteEvent()
}

data class DiaryWriteState(
    val buttonActive: Boolean = true,
    val showLoadingError : Boolean = false,
    val voiceFile : Uri ?= null,
    val imageFiles : List<Uri> = listOf(),
    val feeling: Feeling = Feeling.HAPPY,
    val weather: Weather? = null,
    val currentShowSelectView: CurrentShowSelectView ?= null,
    val musicPlaying : Boolean = false,
    val showInitLoading : Boolean = false,
    val showLocationPickerDialog : Boolean = false,
    val cityName : String ?= null,
    val cityId : Int ?= null,
    val diaryDate: DiaryDate = DiaryDate.getInstanceFromCalendar()
)