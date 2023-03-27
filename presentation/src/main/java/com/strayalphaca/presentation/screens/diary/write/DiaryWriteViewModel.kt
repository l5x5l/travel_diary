package com.strayalphaca.presentation.screens.diary.write

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
class DiaryWriteViewModel @Inject constructor(
    private val useCaseGetDiaryDetail: UseCaseGetDiaryDetail
) : ViewModel() {
    private val events = Channel<DiaryWriteEvent>()
    val state: StateFlow<DiaryWriteState> = events.receiveAsFlow()
        .runningFold(DiaryWriteState(), ::reduce)
        .stateIn(viewModelScope, SharingStarted.Eagerly, DiaryWriteState())

    private val _writingContent = MutableStateFlow("")
    val writingContent = _writingContent.asStateFlow()

    fun tryLoadDetail(id : String) {
        viewModelScope.launch {
            events.send(DiaryWriteEvent.DiaryLoading)

            val response = useCaseGetDiaryDetail(id)
            if (response is BaseResponse.Success) {
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

    private fun reduce(state: DiaryWriteState, events: DiaryWriteEvent): DiaryWriteState {
        return when (events) {
            DiaryWriteEvent.DiaryLoading -> {
                state.copy(buttonActive = false, showLoadingError = false)
            }
            DiaryWriteEvent.DiaryLoadingFail -> {
                state.copy(buttonActive = true, showLoadingError = true)
            }
            is DiaryWriteEvent.DiaryLoadingSuccess -> {
                inputContent(events.diaryDetail.content)
                state.copy(buttonActive = true, showLoadingError = false, diaryDetail = events.diaryDetail)
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
}

data class DiaryWriteState(
    val diaryDetail: DiaryDetail? = null,
    val buttonActive: Boolean = true,
    val showLoadingError : Boolean = false
)