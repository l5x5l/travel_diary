package com.strayalphaca.presentation.screens.diary.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.strayalphaca.domain.diary.model.DiaryDetail
import com.strayalphaca.domain.diary.use_case.UseCaseGetDiaryDetail
import com.strayalphaca.domain.model.BaseResponse
import com.strayalphaca.presentation.screens.diary.model.MusicPlayer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiaryDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val useCaseGetDiaryDetail: UseCaseGetDiaryDetail,
    private val musicPlayer: MusicPlayer
) : ViewModel() {

    private val event = Channel<DiaryDetailEvent>()
    val state : StateFlow<DiaryDetailState> = event.receiveAsFlow()
        .runningFold(DiaryDetailState(), ::reduce)
        .stateIn(viewModelScope, SharingStarted.Eagerly, DiaryDetailState())

    private var musicPlayerJob : Job? = null

    private val _musicProgress = MutableStateFlow(0f)
    val musicProgress = _musicProgress.asStateFlow()

    private val id = savedStateHandle.getStateFlow("diary_id", "")

    fun tryRefresh() {
        viewModelScope.launch {
            event.send(DiaryDetailEvent.DiaryLoading)

            val response = useCaseGetDiaryDetail(id.value)
            if (response is BaseResponse.Success) {
                event.send(DiaryDetailEvent.DiaryLoadingSuccess(response.data))
            } else {
                event.send(DiaryDetailEvent.DiaryLoadingFail)
            }
        }
    }

    fun tryLoadDetail(id : String) {
        if (state.value.diaryDetail != null) return

        viewModelScope.launch {
            event.send(DiaryDetailEvent.DiaryLoading)

            val response = useCaseGetDiaryDetail(id)
            if (response is BaseResponse.Success) {
                event.send(DiaryDetailEvent.DiaryLoadingSuccess(response.data))
            } else {
                event.send(DiaryDetailEvent.DiaryLoadingFail)
            }
        }
    }

    fun playMusic() {
        viewModelScope.launch {
            musicPlayer.playMusic()
            event.send(DiaryDetailEvent.PlayingMusic)
        }
    }

    fun pauseMusic() {
        viewModelScope.launch {
            musicPlayer.stopMusic()
            event.send(DiaryDetailEvent.PauseMusic)
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

    private fun reduce(state : DiaryDetailState, event : DiaryDetailEvent) : DiaryDetailState {
        return when(event) {
            DiaryDetailEvent.DiaryLoading -> {
                state.copy(diaryDetail = null, showError = false)
            }
            DiaryDetailEvent.DiaryLoadingFail -> {
                state.copy(showError = true)
            }
            is DiaryDetailEvent.DiaryLoadingSuccess -> {
                state.copy(diaryDetail = event.diaryDetail, showError = false)
            }
            DiaryDetailEvent.PauseMusic -> {
                musicPlayerJob?.cancel()
                state.copy(musicPlaying = false)
            }
            DiaryDetailEvent.PlayingMusic -> {
                startMusicPlayerJob()
                state.copy(musicPlaying = true)
            }
        }
    }
}

sealed class DiaryDetailEvent {
    object DiaryLoading : DiaryDetailEvent()
    object DiaryLoadingFail : DiaryDetailEvent()
    class DiaryLoadingSuccess(val diaryDetail: DiaryDetail) : DiaryDetailEvent()
    object PlayingMusic : DiaryDetailEvent()
    object PauseMusic : DiaryDetailEvent()
}

data class DiaryDetailState (
    val diaryDetail: DiaryDetail ?= null,
    val showError : Boolean = false,
    val musicPlaying : Boolean = false
)