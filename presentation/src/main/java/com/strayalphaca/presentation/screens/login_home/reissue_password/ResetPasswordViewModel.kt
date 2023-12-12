package com.strayalphaca.presentation.screens.login_home.reissue_password

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.strayalphaca.domain.model.BaseResponse
import com.strayalphaca.presentation.R
import com.strayalphaca.presentation.models.SignupData
import com.strayalphaca.presentation.models.Timer
import com.strayalphaca.presentation.utils.toTimerFormat
import com.strayalphaca.travel_diary.domain.login.use_case.UseCaseCheckAuthCode
import com.strayalphaca.travel_diary.domain.login.use_case.UseCaseIssueAuthCode
import com.strayalphaca.travel_diary.domain.login.use_case.UseCaseResetPassword
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResetPasswordViewModel @Inject constructor(
    private val useCaseCheckAuthCode: UseCaseCheckAuthCode,
    private val useCaseIssueAuthCode: UseCaseIssueAuthCode,
    private val useCaseResetPassword: UseCaseResetPassword
) : ViewModel() {
    private val _viewState = MutableStateFlow<ResetPasswordViewState>(ResetPasswordViewState.InputEmailStep)
    val viewState = _viewState.asStateFlow()

    private val _email = MutableStateFlow("")
    val email = _email.asStateFlow()

    private val _authCode = MutableStateFlow("")
    val authCode = _authCode.asStateFlow()

    private val _showResetPasswordSuccessDialog = MutableStateFlow(false)
    val showResetPasswordSuccessDialog = _showResetPasswordSuccessDialog.asStateFlow()

    private val _showToastEvent = MutableSharedFlow<String>()
    val showToastEvent = _showToastEvent.asSharedFlow()

    private val _timerValue = MutableStateFlow("")
    val timerValue = _timerValue.asStateFlow()

    private val timer = Timer(
        coroutineScope = viewModelScope,
        action = { second ->
            _timerValue.value = toTimerFormat(second)
        }
    )

    private val signupData = SignupData.clearAndGetInstance()

    fun inputEmail(email : String) {
        _email.value = email
    }

    fun inputAuthCode(authCode : String) {
        _authCode.value = authCode
    }

    fun backToInputEmailStep() {
        _viewState.value = ResetPasswordViewState.InputEmailStep
        _authCode.value = ""
        signupData.clearEmail()
    }

    fun tryReIssueAuthCode() {
        viewModelScope.launch {
            _viewState.value = ResetPasswordViewState.ReIssuingAuthCode
            val response = useCaseIssueAuthCode(email.value)

            // 인증번호 재발급 성공시 타이머 초기화
            if (response is BaseResponse.EmptySuccess) {
                timer.refresh()
            }
            if (response is BaseResponse.Failure) {
                _showToastEvent.emit(response.errorMessage)
            }

            _viewState.value = ResetPasswordViewState.AuthCodeStep
        }
    }

    fun tryCheckAndIssueAuthCode() {
        viewModelScope.launch {
            _viewState.value = ResetPasswordViewState.IssuingAuthCode
            val response = useCaseIssueAuthCode(email.value)

            // 인증번호 발급 성공시 타이머 실행
            if (response is BaseResponse.EmptySuccess) {
                timer.start()
                _viewState.value = ResetPasswordViewState.AuthCodeStep
            }
            if (response is BaseResponse.Failure){
                _showToastEvent.emit(response.errorMessage)
                _viewState.value = ResetPasswordViewState.InputEmailStep
            }
        }
    }

    fun tryCheckAuthCode() {
        viewModelScope.launch {
            _viewState.value = ResetPasswordViewState.CheckingAuthCode
            val authCodeCheckResponse = useCaseCheckAuthCode(email.value, authCode.value)

            if (authCodeCheckResponse is BaseResponse.Failure) {
                _showToastEvent.emit(authCodeCheckResponse.errorMessage)
                _viewState.value = ResetPasswordViewState.AuthCodeStep
                return@launch
            }

            timer.clear()
            signupData.setEmail(email.value)

            resetPassword()
        }
    }

    private suspend fun resetPassword() {
        val response = useCaseResetPassword(email.value)

        if (response is BaseResponse.EmptySuccess) {
            _showResetPasswordSuccessDialog.value = true
        }
        if (response is BaseResponse.Failure){
            _showToastEvent.emit(response.errorMessage)
        }
    }

    fun hideResetPasswordSuccessDialog() {
        _showResetPasswordSuccessDialog.value = false
    }

}

sealed class ResetPasswordViewState(
    val isActiveEmailTextField : Boolean,
    val isShowChangeEmailButton : Boolean,
    val isShowAuthCodeArea : Boolean,
    val isActiveAuthCodeTextField : Boolean = false,
    val isActiveRequestAuthCodeButton : Boolean = false,
    val bottomButtonActive : Boolean = true,
    val bottomButtonTextResource : Int
) {
    object InputEmailStep : ResetPasswordViewState(
        isActiveEmailTextField = true,
        isShowChangeEmailButton = false,
        isShowAuthCodeArea = false,
        bottomButtonTextResource = R.string.request_auth_code
    )

    object IssuingAuthCode : ResetPasswordViewState(
        isActiveEmailTextField = false,
        isShowChangeEmailButton = false,
        isShowAuthCodeArea = false,
        bottomButtonTextResource = R.string.request_auth_code,
        bottomButtonActive = false
    )

    object AuthCodeStep : ResetPasswordViewState(
        isActiveEmailTextField = false,
        isShowChangeEmailButton = true,
        isShowAuthCodeArea = true,
        isActiveAuthCodeTextField = true,
        isActiveRequestAuthCodeButton = true,
        bottomButtonTextResource = R.string.reset_password,
    )

    object ReIssuingAuthCode : ResetPasswordViewState(
        isActiveEmailTextField = false,
        isShowChangeEmailButton = true,
        isShowAuthCodeArea = true,
        bottomButtonTextResource = R.string.reset_password,
        bottomButtonActive = false
    )

    object CheckingAuthCode : ResetPasswordViewState(
        isActiveEmailTextField = false,
        isShowChangeEmailButton = true,
        isShowAuthCodeArea = true,
        bottomButtonTextResource = R.string.reset_password,
        bottomButtonActive = false
    )
}