package com.strayalphaca.presentation.screens.diary.detail

import androidx.core.net.toUri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.strayalphaca.travel_diary.diary.model.DiaryDetail
import com.strayalphaca.travel_diary.diary.use_case.UseCaseDeleteDiary
import com.strayalphaca.travel_diary.diary.use_case.UseCaseGetDiaryDetail
import com.strayalpaca.travel_diary.core.domain.model.BaseResponse
import com.strayalphaca.presentation.models.event_flow.MutableEventFlow
import com.strayalphaca.presentation.models.event_flow.asEventFlow
import com.strayalphaca.presentation.screens.diary.model.MusicPlayer
import com.strayalphaca.travel_diary.domain.calendar.usecase.UseCaseHandleCachedCalendarDiary
import com.strayalphaca.travel_diary.map.usecase.UseCaseRefreshCachedMap
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
    private val useCaseHandleCachedCalendarDiary: UseCaseHandleCachedCalendarDiary,
    private val useCaseRefreshCachedMap: UseCaseRefreshCachedMap,
    private val useCaseGetDiaryDetail: UseCaseGetDiaryDetail,
    private val useCaseDeleteDiary : UseCaseDeleteDiary,
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

    private val _goBackNavigationEvent = MutableEventFlow<Boolean>()
    val goBackNavigationEvent = _goBackNavigationEvent.asEventFlow()

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

    fun tryDeleteDiary() {
        if (state.value.diaryDetail == null) return

        viewModelScope.launch {
            event.send(DiaryDetailEvent.DeleteDiaryLoading)
            val response = useCaseDeleteDiary(id.value)
            if (response is BaseResponse.EmptySuccess) {
                event.send(DiaryDetailEvent.DeleteDiarySuccess)
            } else {
                event.send(DiaryDetailEvent.DeleteDiaryFail)
            }
        }
    }

    fun playMusic() {
        if (state.value.deleteLoading) return
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

    fun showDeleteDialog() {
        viewModelScope.launch {
            event.send(DiaryDetailEvent.SetShowDeleteDialog(visible = true))
        }
    }

    fun hideDeleteDialog() {
        viewModelScope.launch {
            event.send(DiaryDetailEvent.SetShowDeleteDialog(visible = false))
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

    private fun reduce(state : DiaryDetailState, event : DiaryDetailEvent) : DiaryDetailState {
        return when(event) {
            DiaryDetailEvent.DiaryLoading -> {
                state.copy(diaryDetail = null, showError = false)
            }
            DiaryDetailEvent.DiaryLoadingFail -> {
                state.copy(showError = true)
            }
            is DiaryDetailEvent.DiaryLoadingSuccess -> {
                event.diaryDetail.voiceFile?.fileLink?.let { musicPlayer.setMusic(it.toUri(), false) }
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
            DiaryDetailEvent.DeleteDiaryLoading -> {
                musicPlayerJob?.cancel()
                state.copy(deleteLoading = true, musicPlaying = false, showDeleteDialog = false)
            }
            DiaryDetailEvent.DeleteDiaryFail -> {
                state.copy(deleteLoading = false)
            }
            DiaryDetailEvent.DeleteDiarySuccess -> {
                deleteDiaryToCachedDataStore()
                occurNavigationBack()
                state.copy(deleteLoading = false)
            }
            is DiaryDetailEvent.SetShowDeleteDialog -> {
                state.copy(showDeleteDialog = event.visible)
            }
        }
    }

    private fun occurNavigationBack() {
        viewModelScope.launch {
            _goBackNavigationEvent.emit(true)
        }
    }

    private fun deleteDiaryToCachedDataStore() {
        viewModelScope.launch {
            useCaseHandleCachedCalendarDiary.delete(id.value)
            useCaseRefreshCachedMap(id.value)
        }
    }
}

sealed class DiaryDetailEvent {
    object DiaryLoading : DiaryDetailEvent()
    object DiaryLoadingFail : DiaryDetailEvent()
    class DiaryLoadingSuccess(val diaryDetail: DiaryDetail) : DiaryDetailEvent()
    object PlayingMusic : DiaryDetailEvent()
    object PauseMusic : DiaryDetailEvent()
    object DeleteDiaryLoading : DiaryDetailEvent()
    object DeleteDiaryFail : DiaryDetailEvent()
    object DeleteDiarySuccess : DiaryDetailEvent()
    class SetShowDeleteDialog(val visible : Boolean) : DiaryDetailEvent()
}

data class DiaryDetailState (
    val diaryDetail: DiaryDetail ?= null,
    val showError : Boolean = false,
    val musicPlaying : Boolean = false,
    val deleteLoading : Boolean = false,
    val showDeleteDialog : Boolean = false
)