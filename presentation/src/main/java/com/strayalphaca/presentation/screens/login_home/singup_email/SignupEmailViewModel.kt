package com.strayalphaca.presentation.screens.login_home.singup_email

import androidx.lifecycle.ViewModel
import com.strayalphaca.presentation.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SignupEmailViewModel : ViewModel() {
    private val _viewState = MutableStateFlow<SignupEmailViewState>(SignupEmailViewState.InputEmailStep)
    val viewState = _viewState.asStateFlow()

    private val _email = MutableStateFlow("")
    val email = _email.asStateFlow()

    private val _authCode = MutableStateFlow("")
    val authCode = _authCode.asStateFlow()

    fun inputEmail(email : String) {
        _email.value = email
    }

    fun inputAuthCode(authCode : String) {
        _authCode.value = authCode
    }

    fun moveToAuthCodeStep() {
        _viewState.value = SignupEmailViewState.AuthCodeStep
    }

    fun backToInputEmailStep() {
        _viewState.value = SignupEmailViewState.InputEmailStep
        _authCode.value = ""
    }
}

sealed class SignupEmailViewState(
    val isActiveEmailTextField : Boolean,
    val isShowChangeEmailButton : Boolean,
    val isShowAuthCodeArea : Boolean,
    val isActiveAuthCodeTextField : Boolean = false,
    val isActiveRequestAuthCodeButton : Boolean = false,
    val bottomButtonTextResource : Int
) {
    object InputEmailStep : SignupEmailViewState(
        isActiveEmailTextField= true,
        isShowChangeEmailButton = false,
        isShowAuthCodeArea = false,
        bottomButtonTextResource = R.string.request_auth_code
    )

    object AuthCodeStep : SignupEmailViewState(
        isActiveEmailTextField = false,
        isShowChangeEmailButton = true,
        isShowAuthCodeArea = true,
        isActiveAuthCodeTextField = true,
        isActiveRequestAuthCodeButton = true,
        bottomButtonTextResource = R.string.move_to_next_step
    )
}