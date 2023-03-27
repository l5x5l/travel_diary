package com.strayalphaca.presentation.screens.diary.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.strayalphaca.domain.diary.model.DiaryDetail
import com.strayalphaca.domain.diary.use_case.UseCaseGetDiaryDetail
import com.strayalphaca.domain.model.BaseResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiaryDetailViewModel @Inject constructor(
    private val useCaseGetDiaryDetail: UseCaseGetDiaryDetail
) : ViewModel() {

    private val event = Channel<DiaryDetailEvent>()
    val state : StateFlow<DiaryDetailState> = event.receiveAsFlow()
        .runningFold(DiaryDetailState(), ::reduce)
        .stateIn(viewModelScope, SharingStarted.Eagerly, DiaryDetailState())

    fun tryLoadDetail(id : String) {
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
        }
    }
}

sealed class DiaryDetailEvent {
    object DiaryLoading : DiaryDetailEvent()
    object DiaryLoadingFail : DiaryDetailEvent()
    class DiaryLoadingSuccess(val diaryDetail: DiaryDetail) : DiaryDetailEvent()
}

data class DiaryDetailState (
    val diaryDetail: DiaryDetail ?= null,
    val showError : Boolean = false
)