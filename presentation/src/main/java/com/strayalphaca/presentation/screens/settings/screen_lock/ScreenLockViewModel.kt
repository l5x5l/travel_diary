package com.strayalphaca.presentation.screens.settings.screen_lock

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.strayalpaca.travel_diary.domain.lock.use_case.UseCaseUsePassword
import com.strayalphaca.presentation.screens.settings.screen_lock.model.ScreenLockPasswordDialogType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScreenLockViewModel @Inject constructor(
    private val useCaseUsePassword: UseCaseUsePassword
) : ViewModel() {
    private val _usePassword = MutableStateFlow(false)
    val usePassword = _usePassword.asStateFlow()

    private val _dialogState = MutableStateFlow<ScreenLockPasswordDialogType?>(null)
    val dialogState = _dialogState.asStateFlow()

    init {
        viewModelScope.launch {
            useCaseUsePassword.flow().collectLatest {
                _usePassword.value = it
            }
        }
    }

    fun setUsePassword(use : Boolean) {
        if (use) {
            _dialogState.update { ScreenLockPasswordDialogType.APPLY }
        } else {
            _dialogState.update { ScreenLockPasswordDialogType.CANCEL }
        }
    }

    fun tryChangePassword() {
        _dialogState.update { ScreenLockPasswordDialogType.CHANGE }
    }

    fun dismissDialog() {
        _dialogState.update { null }
    }
}