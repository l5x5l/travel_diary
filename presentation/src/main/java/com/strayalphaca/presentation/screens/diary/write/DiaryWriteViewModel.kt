package com.strayalphaca.presentation.screens.diary.write

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.strayalphaca.domain.diary.model.DiaryDetail
import com.strayalphaca.domain.diary.model.Feeling
import com.strayalphaca.domain.diary.model.Weather
import com.strayalphaca.domain.diary.use_case.UseCaseGetDiaryDetail
import com.strayalphaca.domain.model.BaseResponse
import com.strayalphaca.presentation.screens.diary.model.CurrentShowSelectView
import com.strayalphaca.presentation.screens.diary.model.MusicPlayer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiaryWriteViewModel @Inject constructor(
    private val useCaseGetDiaryDetail: UseCaseGetDiaryDetail,
    private val musicPlayer: MusicPlayer
) : ViewModel() {
    private val events = Channel<DiaryWriteEvent>()
    val state: StateFlow<DiaryWriteState> = events.receiveAsFlow()
        .runningFold(DiaryWriteState(), ::reduce)
        .stateIn(viewModelScope, SharingStarted.Eagerly, DiaryWriteState())

    private val _writingContent = MutableStateFlow("")
    val writingContent = _writingContent.asStateFlow()

    private val _musicProgress = MutableStateFlow(0f)
    val musicProgress = _musicProgress.asStateFlow()

    private var musicPlayerJob : Job? = null

    init {
        musicPlayer.setCompleteCallback {
            viewModelScope.launch {
                events.send(DiaryWriteEvent.PauseMusic)
            }
        }
    }

    fun tryLoadDetail(id : String) {
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
            events.send(DiaryWriteEvent.AddImageFile(file))
        }
    }

    fun removeImageFile() {
        viewModelScope.launch {
            events.send(DiaryWriteEvent.RemoveImageFile)
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

    fun hideSelectView() {
        viewModelScope.launch {
            events.send(DiaryWriteEvent.HideSelectView)
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

    private fun reduce(state: DiaryWriteState, events: DiaryWriteEvent): DiaryWriteState {
        return when (events) {
            DiaryWriteEvent.DiaryLoading -> {
                state.copy(buttonActive = false, showLoadingError = false)
            }
            DiaryWriteEvent.DiaryLoadingFail -> {
                state.copy(buttonActive = true, showLoadingError = true)
            }
            is DiaryWriteEvent.DiaryLoadingSuccess -> {
                state.copy(buttonActive = true, showLoadingError = false, feeling = events.diaryDetail.feeling, weather = events.diaryDetail.weather)
            }
            DiaryWriteEvent.DiaryWriteLoading -> {
                state.copy(buttonActive = false)
            }
            DiaryWriteEvent.DiaryWriteFail -> {
                state.copy(buttonActive = true)
            }
            DiaryWriteEvent.DiaryWriteSuccess -> {
                state.copy(buttonActive = true)
            }
            is DiaryWriteEvent.AddVoiceFile -> {
                state.copy(voiceFile = events.file)
            }
            DiaryWriteEvent.RemoveVoiceFile -> {
                state.copy(voiceFile = null)
            }
            is DiaryWriteEvent.AddImageFile -> {
                state.copy(imageFiles = events.file)
            }
            DiaryWriteEvent.RemoveImageFile -> {
                state.copy(imageFiles = listOf())
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
    class AddImageFile(val file : List<Uri>) : DiaryWriteEvent()
    object RemoveImageFile : DiaryWriteEvent()
    class SetFeeling(val feeling: Feeling) : DiaryWriteEvent()
    class SetWeather(val weather: Weather) : DiaryWriteEvent()
    object ShowSelectFeelingView : DiaryWriteEvent()
    object ShowSelectWeatherView : DiaryWriteEvent()
    object HideSelectView : DiaryWriteEvent()
    object PlayingMusic : DiaryWriteEvent()
    object PauseMusic : DiaryWriteEvent()
}

data class DiaryWriteState(
    val buttonActive: Boolean = true,
    val showLoadingError : Boolean = false,
    val voiceFile : Uri ?= null,
    val imageFiles : List<Uri> = listOf(),
    val feeling: Feeling = Feeling.HAPPY,
    val weather: Weather? = null,
    val currentShowSelectView: CurrentShowSelectView ?= null,
    val musicPlaying : Boolean = false
)