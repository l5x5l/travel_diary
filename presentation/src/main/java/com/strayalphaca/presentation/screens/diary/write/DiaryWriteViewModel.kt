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

    fun inputVoiceFile(file : ByteArray) {
        viewModelScope.launch {
            events.send(DiaryWriteEvent.AddVoiceFile(file))
        }
    }

    fun removeVoiceFile() {
        viewModelScope.launch {
            events.send(DiaryWriteEvent.RemoveVoiceFile)
        }
    }

    fun inputImageFile(file : ByteArray) {
        viewModelScope.launch {
            events.send(DiaryWriteEvent.AddImageFile(file))
        }
    }

    fun removeImageFile() {
        viewModelScope.launch {
            events.send(DiaryWriteEvent.RemoveImageFile)
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
                state.copy(buttonActive = true, showLoadingError = false)
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
                state.copy(imageFiles = listOf(events.file))
            }
            DiaryWriteEvent.RemoveImageFile -> {
                state.copy(imageFiles = listOf())
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
    class AddVoiceFile(val file : ByteArray) : DiaryWriteEvent()
    object RemoveVoiceFile : DiaryWriteEvent()
    class AddImageFile(val file : ByteArray) : DiaryWriteEvent()
    object RemoveImageFile : DiaryWriteEvent()
}

data class DiaryWriteState(
    val buttonActive: Boolean = true,
    val showLoadingError : Boolean = false,
    val voiceFile : ByteArray ?= null,
    val imageFiles : List<ByteArray> = listOf()
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DiaryWriteState

        if (buttonActive != other.buttonActive) return false
        if (showLoadingError != other.showLoadingError) return false
        if (voiceFile != null) {
            if (other.voiceFile == null) return false
            if (!voiceFile.contentEquals(other.voiceFile)) return false
        } else if (other.voiceFile != null) return false
        if (imageFiles != other.imageFiles) return false

        return true
    }

    override fun hashCode(): Int {
        var result = buttonActive.hashCode()
        result = 31 * result + showLoadingError.hashCode()
        result = 31 * result + (voiceFile?.contentHashCode() ?: 0)
        result = 31 * result + imageFiles.hashCode()
        return result
    }


}