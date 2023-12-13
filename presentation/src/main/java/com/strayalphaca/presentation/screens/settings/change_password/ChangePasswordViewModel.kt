package com.strayalphaca.presentation.screens.settings.change_password

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.runningFold
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(

) : ViewModel() {
    private val _prevPassword = MutableStateFlow("")
    val prevPassword = _prevPassword.asStateFlow()

    private val _newPassword = MutableStateFlow("")
    val newPassword = _newPassword.asStateFlow()

    private val events = Channel<ChangePasswordScreenEvent>()
    val state: StateFlow<ChangePasswordScreenState> = events.receiveAsFlow()
        .runningFold(ChangePasswordScreenState(), ::reduce)
        .stateIn(viewModelScope, SharingStarted.Eagerly, ChangePasswordScreenState())

    fun inputPrePassword(prevPassword : String) {
        _prevPassword.value = prevPassword
    }

    fun inputNewPassword(newPassword : String) {
        _newPassword.value = newPassword
    }

    fun tryChangePassword() {
        viewModelScope.launch {
            events.send(ChangePasswordScreenEvent.ChangePasswordLoading)

            delay(1000L)
            // todo 비밀번호 변경 usecase 등록

            events.send(ChangePasswordScreenEvent.ChangePasswordFailure("아직 구현하지 않았습니다."))
        }
    }

    private fun reduce(
        state: ChangePasswordScreenState,
        event: ChangePasswordScreenEvent
    ): ChangePasswordScreenState {
        return when(event) {
            is ChangePasswordScreenEvent.ChangePasswordFailure -> {
                state.copy(buttonEnable = true, errorMessage = event.message)
            }
            ChangePasswordScreenEvent.ChangePasswordLoading -> {
                state.copy(buttonEnable = false, errorMessage = "")
            }
            ChangePasswordScreenEvent.ChangePasswordSuccess -> {
                state.copy(buttonEnable = true)
            }
        }
    }
}

data class ChangePasswordScreenState(
    val buttonEnable : Boolean = true,
    val errorMessage : String = ""
)

sealed class ChangePasswordScreenEvent {
    object ChangePasswordLoading : ChangePasswordScreenEvent()
    object ChangePasswordSuccess : ChangePasswordScreenEvent()
    class ChangePasswordFailure(val message : String) : ChangePasswordScreenEvent()
}