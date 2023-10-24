package com.strayalphaca.presentation.screens.login_home.singup_email

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.strayalphaca.travel_diary.domain.login.use_case.UseCaseCheckAuthCode
import com.strayalphaca.travel_diary.domain.login.use_case.UseCaseIssueAuthCode
import com.strayalphaca.domain.model.BaseResponse
import com.strayalphaca.presentation.R
import com.strayalphaca.presentation.models.SignupData
import com.strayalphaca.presentation.models.Timer
import com.strayalphaca.presentation.utils.toTimerFormat
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupEmailViewModel @Inject constructor(
    private val useCaseCheckAuthCode: UseCaseCheckAuthCode,
    private val useCaseIssueAuthCode: UseCaseIssueAuthCode
) : ViewModel() {
    private val _viewState = MutableStateFlow<SignupEmailViewState>(SignupEmailViewState.InputEmailStep)
    val viewState = _viewState.asStateFlow()

    private val _email = MutableStateFlow("")
    val email = _email.asStateFlow()

    private val _authCode = MutableStateFlow("")
    val authCode = _authCode.asStateFlow()

    private val _moveToPasswordEvent = MutableSharedFlow<Boolean>()
    val moveToPasswordEvent = _moveToPasswordEvent.asSharedFlow()

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
        _viewState.value = SignupEmailViewState.InputEmailStep
        _authCode.value = ""
        signupData.clearEmail()
    }

    fun tryReIssueAuthCode() {
        viewModelScope.launch {
            _viewState.value = SignupEmailViewState.ReIssuingAuthCodeStep
            val response = useCaseIssueAuthCode(email.value)

            // 재발급 성공시 타이머 초기화
            if (response is BaseResponse.EmptySuccess) {
                timer.refresh()
            }
            if (response is BaseResponse.Failure) {
                _showToastEvent.emit(response.errorMessage)
            }

            _viewState.value = SignupEmailViewState.AuthCodeStep
        }
    }

    fun tryCheckAndIssueAuthCode() {
        viewModelScope.launch {
            _viewState.value = SignupEmailViewState.IssuingAuthCodeStep
            val response = useCaseIssueAuthCode(email.value)

            // 이메일 중복검사 및 인증번호 발급 성공시 타이머 실행
            if (response is BaseResponse.EmptySuccess) {
                timer.start()
                _viewState.value = SignupEmailViewState.AuthCodeStep
            }
            if (response is BaseResponse.Failure){
                _showToastEvent.emit(response.errorMessage)
                _viewState.value = SignupEmailViewState.InputEmailStep
            }
        }
    }

    fun tryCheckAuthCode() {
        viewModelScope.launch {
            _viewState.value = SignupEmailViewState.CheckingAuthCode
            val response = useCaseCheckAuthCode(email.value, authCode.value)

            // 인증번호 성공 후 다음 화면으로 이동하기 전 타이머 초기화
            if (response is BaseResponse.EmptySuccess) {
                timer.clear()
                signupData.setEmail(email.value)
                _moveToPasswordEvent.emit(true)
            }
            if (response is BaseResponse.Failure) {
                _showToastEvent.emit(response.errorMessage)
            }

            _viewState.value = SignupEmailViewState.AuthCodeStep
        }
    }


}

sealed class SignupEmailViewState(
    val isActiveEmailTextField : Boolean,
    val isShowChangeEmailButton : Boolean,
    val isShowAuthCodeArea : Boolean,
    val isActiveAuthCodeTextField : Boolean = false,
    val isActiveRequestAuthCodeButton : Boolean = false,
    val bottomButtonActive : Boolean = true,
    val bottomButtonTextResource : Int
) {
    object InputEmailStep : SignupEmailViewState(
        isActiveEmailTextField= true,
        isShowChangeEmailButton = false,
        isShowAuthCodeArea = false,
        bottomButtonTextResource = R.string.request_auth_code
    )

    object IssuingAuthCodeStep : SignupEmailViewState(
        isActiveEmailTextField = false,
        isShowChangeEmailButton = false,
        isShowAuthCodeArea = false,
        bottomButtonTextResource = R.string.request_auth_code,
        bottomButtonActive = false
    )

    object AuthCodeStep : SignupEmailViewState(
        isActiveEmailTextField = false,
        isShowChangeEmailButton = true,
        isShowAuthCodeArea = true,
        isActiveAuthCodeTextField = true,
        isActiveRequestAuthCodeButton = true,
        bottomButtonTextResource = R.string.move_to_next_step
    )

    object ReIssuingAuthCodeStep : SignupEmailViewState(
        isActiveEmailTextField = false,
        isShowChangeEmailButton = true,
        isShowAuthCodeArea = true,
        isActiveAuthCodeTextField = false,
        isActiveRequestAuthCodeButton = false,
        bottomButtonTextResource = R.string.move_to_next_step,
        bottomButtonActive = false
    )

    object CheckingAuthCode : SignupEmailViewState(
        isActiveEmailTextField = false,
        isShowChangeEmailButton = true,
        isShowAuthCodeArea = true,
        isActiveAuthCodeTextField = false,
        isActiveRequestAuthCodeButton = false,
        bottomButtonTextResource = R.string.move_to_next_step,
        bottomButtonActive = false
    )

}