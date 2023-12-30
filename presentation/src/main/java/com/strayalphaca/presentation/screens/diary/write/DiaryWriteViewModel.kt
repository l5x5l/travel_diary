package com.strayalphaca.presentation.screens.diary.write

import android.net.Uri
import androidx.core.net.toUri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.strayalpaca.travel_diary.core.domain.model.DiaryDate
import com.strayalpaca.travel_diary.core.domain.model.BaseResponse
import com.strayalphaca.presentation.models.event_flow.MutableEventFlow
import com.strayalphaca.presentation.models.event_flow.asEventFlow
import com.strayalphaca.presentation.screens.diary.model.CurrentShowSelectView
import com.strayalphaca.presentation.screens.diary.model.MediaFileInDiary
import com.strayalphaca.presentation.screens.diary.model.MusicPlayer
import com.strayalphaca.presentation.screens.diary.util.getInstanceFromDateString
import com.strayalphaca.presentation.utils.UriHandler
import com.strayalphaca.presentation.utils.mapIf
import com.strayalphaca.travel_diary.diary.model.DiaryDetail
import com.strayalphaca.travel_diary.diary.model.DiaryModifyData
import com.strayalphaca.travel_diary.diary.model.DiaryWriteData
import com.strayalphaca.travel_diary.diary.model.Feeling
import com.strayalphaca.travel_diary.diary.model.Weather
import com.strayalphaca.travel_diary.diary.use_case.UseCaseGetDiaryDetail
import com.strayalphaca.travel_diary.diary.use_case.UseCaseModifyDiary
import com.strayalphaca.travel_diary.diary.use_case.UseCaseUploadDiary
import com.strayalphaca.travel_diary.domain.calendar.model.DiaryInCalendar
import com.strayalphaca.travel_diary.domain.calendar.usecase.UseCaseHandleCachedCalendarDiary
import com.strayalphaca.travel_diary.domain.file.model.FileResizeHandler
import com.strayalphaca.travel_diary.domain.file.model.FileType
import com.strayalphaca.travel_diary.domain.file.usecase.UseCaseUploadFiles
import com.strayalphaca.travel_diary.map.model.City
import com.strayalphaca.travel_diary.map.model.Province
import com.strayalphaca.travel_diary.map.usecase.UseCaseRefreshCachedMap
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiaryWriteViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val useCaseHandleCachedCalendarDiary: UseCaseHandleCachedCalendarDiary,
    private val useCaseRefreshCachedMap: UseCaseRefreshCachedMap,
    private val useCaseGetDiaryDetail: UseCaseGetDiaryDetail,
    private val useCaseUploadFiles: UseCaseUploadFiles,
    private val useCaseUploadDiary: UseCaseUploadDiary,
    private val useCaseModifyDiary: UseCaseModifyDiary,
    private val musicPlayer: MusicPlayer,
    private val uriHandler: UriHandler,
    private val fileResizeHandler: FileResizeHandler
) : ViewModel() {
    private val events = Channel<DiaryWriteEvent>()
    val state: StateFlow<DiaryWriteState> = events.receiveAsFlow()
        .runningFold(DiaryWriteState(), ::reduce)
        .stateIn(viewModelScope, SharingStarted.Eagerly, DiaryWriteState())

    private val _writingContent = MutableStateFlow("")
    val writingContent = _writingContent.asStateFlow()

    private val _goBackNavigationEvent = MutableEventFlow<Boolean>()
    val goBackNavigationEvent = _goBackNavigationEvent.asEventFlow()

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
            if (dateString == "null") return@let
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
            events.send(DiaryWriteEvent.AddImageList(file))
        }
    }

    fun deleteImageFile(file : Uri) {
        viewModelScope.launch {
            events.send(DiaryWriteEvent.DeleteImage(file))
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

            val voiceFileLink = state.value.voiceFile?.let{ uploadFileList(listOf(it)) }
            val mediaFileLinkList = uploadFileList(state.value.imageFiles)

            val uploadFilesSuccess = ((voiceFileLink == null || voiceFileLink is BaseResponse.Success) && mediaFileLinkList is BaseResponse.Success)
            if (!uploadFilesSuccess) {
                events.send(DiaryWriteEvent.DiaryWriteFail)
                return@launch
            }
            mediaFileLinkList as BaseResponse.Success
            voiceFileLink as BaseResponse.Success?

            val mediaFileIdList = mediaFileLinkList.data
            val voiceFileId = voiceFileLink?.data?.getOrNull(0)

            val response = if (diaryId == null) {
                useCaseUploadDiary(
                    DiaryWriteData(
                        feeling = state.value.feeling,
                        weather = state.value.weather,
                        content = writingContent.value,
                        medias = mediaFileIdList,
                        voice = voiceFileId,
                        cityId = state.value.cityId,
                        recordDate = state.value.diaryDate
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
                        cityId = state.value.cityId,
                        date = state.value.diaryDate
                    )
                )
            }

            when (response) {
                is BaseResponse.Success -> { // 새로 작성한 경우, response에 새로 작성된 일지의 id가 같이 리턴됨
                    events.send(DiaryWriteEvent.DiaryWriteSuccess(response.data))
                }
                is BaseResponse.EmptySuccess -> { // 기존 일지를 수정한 경우, 별도의 데이터가 같이 전달되지 않음
                    events.send(DiaryWriteEvent.DiaryWriteSuccess(diaryId!!))
                }
                else -> {
                    events.send(DiaryWriteEvent.DiaryWriteFail)
                }
            }
        }
    }

    private suspend fun uploadFileList(fileList: List<MediaFileInDiary>): BaseResponse<List<String>> {
        val alreadyUploadedFileIndexList = fileList.mapIndexed { index, mediaFileInDiary ->
            if (mediaFileInDiary is MediaFileInDiary.UploadedFile) Pair(index, mediaFileInDiary) else null
        }.filterNotNull()

        val localFileList = fileList.filterIsInstance<MediaFileInDiary.LocalFile>()
            .map { uriHandler.uriToFile(it.uri) }
            .mapIf({it.fileType == FileType.Image}) {
                val resizedFile = fileResizeHandler.resizeImageFile(it.file, 1024 * 1024)
                it.copy(file = resizedFile)
            }

        val response = useCaseUploadFiles(localFileList)

        return if (response is BaseResponse.Success<List<String>>) {
            val uploadFileIdList = response.data.toMutableList()
            for (uploadedFile in alreadyUploadedFileIndexList) {
                uploadFileIdList.add(uploadedFile.first, uploadedFile.second.id)
            }
            response.copy(data = uploadFileIdList)
        } else {
            response
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

    fun selectCityById(cityId : Int?) {
        viewModelScope.launch {
            if (cityId != null) {
                events.send(DiaryWriteEvent.SelectLocationById(cityId))
            } else {
                events.send(DiaryWriteEvent.ClearLocation)
            }
        }
    }

    private fun callGoBackNavigationEvent() {
        viewModelScope.launch {
            _goBackNavigationEvent.emit(true)
        }
    }

    private fun saveWriteDiaryToCachedDataStore(id : String) {
        viewModelScope.launch {
            val isModify = diaryId != null
            val diaryInCalendar = DiaryInCalendar(
                id = id,
                date = state.value.diaryDate,
                thumbnailUrl = state.value.imageFiles.getOrNull(0)?.uri.toString()
            )
            if (isModify) {
                useCaseHandleCachedCalendarDiary.update(diaryInCalendar)
                useCaseRefreshCachedMap(id)
            } else {
                useCaseHandleCachedCalendarDiary.add(diaryInCalendar)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        releaseMusicPlayer()
    }

    private fun releaseMusicPlayer() {
        musicPlayerJob?.cancel()
        musicPlayer.release()
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
                events.diaryDetail.voiceFile?.fileLink?.let { musicPlayer.setMusic(it.toUri(), false) }
                state.copy(
                    buttonActive = true,
                    showLoadingError = false,
                    feeling = events.diaryDetail.feeling,
                    weather = events.diaryDetail.weather,
                    showInitLoading = false,
                    diaryDate = events.diaryDetail.date,
                    voiceFile = events.diaryDetail.voiceFile?.let { MediaFileInDiary.UploadedFile.createFromFile(it) },
                    imageFiles = events.diaryDetail.files.map { MediaFileInDiary.UploadedFile.createFromFile(it) },
                    cityId = events.diaryDetail.cityId,
                    cityName = events.diaryDetail.cityName
                )
            }
            DiaryWriteEvent.DiaryWriteLoading -> {
                state.copy(buttonActive = false)
            }
            DiaryWriteEvent.DiaryWriteFail -> {
                state.copy(buttonActive = true)
            }
            is DiaryWriteEvent.DiaryWriteSuccess -> {
                callGoBackNavigationEvent()
                saveWriteDiaryToCachedDataStore(events.id)
                state.copy(buttonActive = true)
            }
            is DiaryWriteEvent.AddVoiceFile -> {
                state.copy(voiceFile = MediaFileInDiary.LocalFile(localUri = events.file, fileType = FileType.Voice))
            }
            DiaryWriteEvent.RemoveVoiceFile -> {
                state.copy(voiceFile = null)
            }
            is DiaryWriteEvent.AddImageList -> {
                val newImages = events.file.map { MediaFileInDiary.LocalFile(localUri = it, fileType = uriHandler.getFileType(it)) }
                val images = (state.imageFiles + newImages)
                state.copy(imageFiles = images.subList(0, 3.coerceAtMost(images.size)))
            }
            is DiaryWriteEvent.DeleteImage -> {
                val imageFiles = state.imageFiles.filter { !it.checkUri(events.file) }
                state.copy(imageFiles = imageFiles)
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
            DiaryWriteEvent.ClearLocation -> {
                state.copy(cityName = null, cityId = null)
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
    class DiaryWriteSuccess(val id : String) : DiaryWriteEvent()
    class AddVoiceFile(val file : Uri) : DiaryWriteEvent()
    object RemoveVoiceFile : DiaryWriteEvent()
    class AddImageList(val file : List<Uri>) : DiaryWriteEvent()
    class DeleteImage(val file : Uri) : DiaryWriteEvent()
    class SetFeeling(val feeling: Feeling) : DiaryWriteEvent()
    class SetWeather(val weather: Weather) : DiaryWriteEvent()
    object ShowSelectFeelingView : DiaryWriteEvent()
    object ShowSelectWeatherView : DiaryWriteEvent()
    object HideSelectView : DiaryWriteEvent()
    object PlayingMusic : DiaryWriteEvent()
    object PauseMusic : DiaryWriteEvent()
    class SetShowLocationPickerDialog(val show : Boolean) : DiaryWriteEvent()
    class SelectLocationById(val cityId : Int) : DiaryWriteEvent()
    object ClearLocation : DiaryWriteEvent()
    class SetDiaryDate(val diaryDate: DiaryDate) : DiaryWriteEvent()
}

data class DiaryWriteState(
    val buttonActive: Boolean = true,
    val showLoadingError : Boolean = false,
    val voiceFile : MediaFileInDiary ?= null,
    val imageFiles : List<MediaFileInDiary> = listOf(),
    val feeling: Feeling = Feeling.HAPPY,
    val weather: Weather = Weather.SUNNY,
    val currentShowSelectView: CurrentShowSelectView ?= null,
    val musicPlaying : Boolean = false,
    val showInitLoading : Boolean = false,
    val showLocationPickerDialog : Boolean = false,
    val cityName : String ?= null,
    val cityId : Int ?= null,
    val diaryDate: DiaryDate = DiaryDate.getInstanceFromCalendar()
)