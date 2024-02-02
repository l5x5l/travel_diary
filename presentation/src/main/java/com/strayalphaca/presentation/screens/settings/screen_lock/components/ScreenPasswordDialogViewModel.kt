package com.strayalphaca.presentation.screens.settings.screen_lock.components

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.strayalpaca.travel_diary.core.domain.model.BaseResponse
import com.strayalpaca.travel_diary.domain.lock.use_case.UseCaseCheckPassword
import com.strayalpaca.travel_diary.domain.lock.use_case.UseCaseRemovePassword
import com.strayalpaca.travel_diary.domain.lock.use_case.UseCaseSavePassword
import com.strayalphaca.presentation.models.event_flow.MutableEventFlow
import com.strayalphaca.presentation.models.event_flow.asEventFlow
import com.strayalphaca.presentation.screens.settings.screen_lock.model.ScreenLockPasswordDialogEvent
import com.strayalphaca.presentation.screens.settings.screen_lock.model.ScreenLockPasswordDialogState
import com.strayalphaca.presentation.screens.settings.screen_lock.model.ScreenLockPasswordDialogType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScreenPasswordDialogViewModel @Inject constructor(
    private val useCaseCheckPassword: UseCaseCheckPassword,
    private val useCaseSavePassword: UseCaseSavePassword,
    private val useCaseRemovePassword: UseCaseRemovePassword
) : ViewModel() {
    private val _dismissDialogEvent = MutableEventFlow<Boolean>()
    val dismissDialogEvent = _dismissDialogEvent.asEventFlow()

    private val events = Channel<ScreenLockPasswordDialogEvent>()
    val state : StateFlow<ScreenLockPasswordDialogState> = events.receiveAsFlow().map { reduce(it) }
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            ScreenLockPasswordDialogState.InputNewPassword(
                onFillTextEvent = ::saveTempNewPassword,
                rightButtonPressEvent = ::dismiss
            )
        )

    private val _inputPassword = MutableStateFlow("")
    val inputPassword = _inputPassword.asStateFlow()

    private var tempPassword = ""

    fun setInputPassword(password : String) {
        _inputPassword.update { password }
    }

    private fun dismiss() {
        viewModelScope.launch {
            tempPassword = ""
            _dismissDialogEvent.emit(true)
        }
    }

    fun setBase(type: ScreenLockPasswordDialogType) {
        viewModelScope.launch {
            events.send(ScreenLockPasswordDialogEvent.SetBaseState(type))
        }
    }

    // 돌아가기 클릭시 이벤트
    private fun moveToNewPasswordInputStep() {
        viewModelScope.launch {
            events.send(ScreenLockPasswordDialogEvent.BackToInputNewPassword)
        }
    }

    // 새 비밀번호 입력 완료시 이벤트
    private fun saveTempNewPassword(inputPassword : String) {
        viewModelScope.launch {
            tempPassword = inputPassword
            events.send(ScreenLockPasswordDialogEvent.FillNewPassword)
        }
    }

    // 새 비밀번호 입력 후 재확인 비밀번호 입력 완료시 이벤트
    private fun compareToTempNewPassword(inputConfirmPassword : String) {
        viewModelScope.launch {
            events.send(ScreenLockPasswordDialogEvent.FillConfirmNewPassword)
            if (inputConfirmPassword == tempPassword) {
                events.send(ScreenLockPasswordDialogEvent.NewPasswordMatched)
            } else {
                events.send(ScreenLockPasswordDialogEvent.NewPasswordNotMatched)
            }
        }
    }

    private fun compareToExistPassword(inputPassword : String, checkPasswordGoal: ScreenLockPasswordDialogEvent.CheckPasswordGoal) {
        viewModelScope.launch {
            events.send(ScreenLockPasswordDialogEvent.FillExistPassword(checkPasswordGoal))
            if (useCaseCheckPassword(inputPassword) is BaseResponse.EmptySuccess) {
                events.send(ScreenLockPasswordDialogEvent.ExistPasswordMatched(checkPasswordGoal))
            } else {
                events.send(ScreenLockPasswordDialogEvent.ExistPasswordNotMatched(checkPasswordGoal))
            }
        }
    }

    private fun reduce(event : ScreenLockPasswordDialogEvent) : ScreenLockPasswordDialogState {
        return when (event) {
            ScreenLockPasswordDialogEvent.FillNewPassword -> { // 새 비밀번호 입력 완료
                _inputPassword.update { "" }
                ScreenLockPasswordDialogState.InputNewPasswordAgain(
                    onFillTextEvent = ::compareToTempNewPassword,
                    leftButtonPressEvent = ::moveToNewPasswordInputStep,
                    rightButtonPressEvent = ::dismiss
                )
            }
            ScreenLockPasswordDialogEvent.BackToInputNewPassword -> { // 새 비밀번호 입력 화면으로 이동
                _inputPassword.update { "" }
                ScreenLockPasswordDialogState.InputNewPassword(
                    onFillTextEvent = ::saveTempNewPassword,
                    rightButtonPressEvent = ::dismiss
                )
            }
            ScreenLockPasswordDialogEvent.FillConfirmNewPassword -> { // 재확인용 비밀번호 입력 완료
                ScreenLockPasswordDialogState.CheckingNewPassword
            }
            ScreenLockPasswordDialogEvent.NewPasswordNotMatched -> { // 직전에 작성한 비밀번호와 맞지 않음
                _inputPassword.update { "" }
                ScreenLockPasswordDialogState.CheckingNewPasswordFail(
                    onFillTextEvent = ::compareToTempNewPassword,
                    leftButtonPressEvent = ::moveToNewPasswordInputStep,
                    rightButtonPressEvent = ::dismiss
                )
            }
            ScreenLockPasswordDialogEvent.NewPasswordMatched -> { // 비밀번호 등록 성공
                viewModelScope.launch {
                    useCaseSavePassword(tempPassword)
                    _dismissDialogEvent.emit(true)
                }
                ScreenLockPasswordDialogState.InputExistPasswordForCancel(
                    onFillTextEvent = {
                        compareToExistPassword(inputPassword = it, checkPasswordGoal = ScreenLockPasswordDialogEvent.CheckPasswordGoal.Cancel)
                    },
                    rightButtonPressEvent = ::dismiss
                )
            }
            is ScreenLockPasswordDialogEvent.FillExistPassword -> { // 기존 비밀번호 입력
                when (event.goal) {
                    ScreenLockPasswordDialogEvent.CheckPasswordGoal.Change -> {
                        ScreenLockPasswordDialogState.CheckingExistPasswordForChange
                    }
                    ScreenLockPasswordDialogEvent.CheckPasswordGoal.Cancel -> {
                        ScreenLockPasswordDialogState.CheckingExistPasswordForCancel
                    }
                }
            }
            is ScreenLockPasswordDialogEvent.ExistPasswordMatched -> { // 기존 비밀번호와 맞음
                _inputPassword.update { "" }
                when (event.goal) {
                    ScreenLockPasswordDialogEvent.CheckPasswordGoal.Change -> {
                        ScreenLockPasswordDialogState.InputNewPassword(
                            onFillTextEvent = ::saveTempNewPassword,
                            rightButtonPressEvent = ::dismiss
                        )
                    }
                    ScreenLockPasswordDialogEvent.CheckPasswordGoal.Cancel -> {
                        viewModelScope.launch {
                            useCaseRemovePassword()
                            _dismissDialogEvent.emit(true)
                        }
                        ScreenLockPasswordDialogState.InputNewPassword(
                            onFillTextEvent = ::saveTempNewPassword,
                            rightButtonPressEvent = ::dismiss
                        )
                    }
                }
            }
            is ScreenLockPasswordDialogEvent.ExistPasswordNotMatched -> { // 기존 비밀번호와 맞지 않음
                _inputPassword.update { "" }
                when (event.goal) {
                    ScreenLockPasswordDialogEvent.CheckPasswordGoal.Change -> {
                        ScreenLockPasswordDialogState.CheckingExistPasswordForChangeFail(
                            onFillTextEvent = {
                                compareToExistPassword(inputPassword = it, checkPasswordGoal = ScreenLockPasswordDialogEvent.CheckPasswordGoal.Change)
                            },
                            rightButtonPressEvent = ::dismiss
                        )
                    }
                    ScreenLockPasswordDialogEvent.CheckPasswordGoal.Cancel -> {
                        ScreenLockPasswordDialogState.CheckingExistPasswordForCancelFail(
                            onFillTextEvent = {
                                compareToExistPassword(inputPassword = it, checkPasswordGoal = ScreenLockPasswordDialogEvent.CheckPasswordGoal.Cancel)
                            },
                            rightButtonPressEvent = ::dismiss
                        )
                    }
                }
            }
            is ScreenLockPasswordDialogEvent.SetBaseState -> { // dialog 생성시 호출 종류에 따라 상태 조정
                _inputPassword.update { "" }
                when (event.type) {
                    ScreenLockPasswordDialogType.APPLY -> {
                        ScreenLockPasswordDialogState.InputNewPassword(
                            onFillTextEvent = ::saveTempNewPassword,
                            rightButtonPressEvent = ::dismiss
                        )
                    }
                    ScreenLockPasswordDialogType.CANCEL -> {
                        ScreenLockPasswordDialogState.InputExistPasswordForCancel(
                            onFillTextEvent = {
                                compareToExistPassword(inputPassword = it, checkPasswordGoal = ScreenLockPasswordDialogEvent.CheckPasswordGoal.Cancel)
                            },
                            rightButtonPressEvent = ::dismiss
                        )
                    }
                    ScreenLockPasswordDialogType.CHANGE -> {
                        ScreenLockPasswordDialogState.InputExistPasswordForChange(
                            onFillTextEvent = {
                                compareToExistPassword(inputPassword = it, checkPasswordGoal = ScreenLockPasswordDialogEvent.CheckPasswordGoal.Change)
                            },
                            rightButtonPressEvent = ::dismiss
                        )
                    }
                }
            }

        }
    }
}