package com.strayalphaca.presentation.screens.lock

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.strayalpaca.travel_diary.domain.lock.use_case.UseCaseCheckPassword
import com.strayalpaca.travel_diary.core.domain.model.BaseResponse
import com.strayalphaca.presentation.models.event_flow.MutableEventFlow
import com.strayalphaca.presentation.models.event_flow.asEventFlow
import com.strayalphaca.presentation.screens.lock.model.LockScreenEvent
import com.strayalphaca.presentation.screens.lock.model.LockScreenSideEffect
import com.strayalphaca.presentation.screens.lock.model.LockScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LockViewModel @Inject constructor(
    private val useCaseCheckPassword: UseCaseCheckPassword
) : ViewModel() {

    private val events = Channel<LockScreenEvent>()
    val state : StateFlow<LockScreenState> = events.receiveAsFlow()
        .runningFold(LockScreenState(), ::reduce)
        .stateIn(viewModelScope, SharingStarted.Eagerly, LockScreenState())

    private val _sideEffect = MutableEventFlow<LockScreenSideEffect>()
    val sideEffect = _sideEffect.asEventFlow()

    fun inputPassword(inputPassword : String) {
        viewModelScope.launch {
            events.send(LockScreenEvent.ChangeInputPassword(inputPassword = inputPassword))

            if (inputPassword.length >= 4) {
                checkPassword(inputPassword = inputPassword)
            }
        }
    }

    private suspend fun checkPassword(inputPassword : String) {
        events.send(LockScreenEvent.PasswordChecking)
        val response = useCaseCheckPassword(inputPassword)

        if (response == BaseResponse.EmptySuccess) {
            _sideEffect.emit(LockScreenSideEffect.CheckComplete)
        } else {
            events.send(LockScreenEvent.ClearInputPassword)
            _sideEffect.emit(LockScreenSideEffect.ShowFailMessage)
        }
    }

    private fun reduce(state : LockScreenState, event : LockScreenEvent) : LockScreenState {
        return when (event) {
            is LockScreenEvent.ChangeInputPassword -> {
                val inputPassword = event.inputPassword.run {
                    return@run if (length <= 4) this else substring(0, 4)
                }
                state.copy(inputPassword = inputPassword)
            }
            LockScreenEvent.ClearInputPassword -> {
                state.copy(inputPassword = "", checkingPassword = false)
            }
            LockScreenEvent.PasswordChecking -> {
                state.copy(checkingPassword = true)
            }
        }
    }
}