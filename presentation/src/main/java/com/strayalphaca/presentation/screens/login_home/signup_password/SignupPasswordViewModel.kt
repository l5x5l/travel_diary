package com.strayalphaca.presentation.screens.login_home.signup_password

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.strayalphaca.travel_diary.domain.login.use_case.UseCaseSignup
import com.strayalpaca.travel_diary.core.domain.model.BaseResponse
import com.strayalphaca.presentation.components.atom.base_button.BaseButtonState
import com.strayalphaca.presentation.components.block.EditTextState
import com.strayalphaca.presentation.models.SignupData
import com.strayalphaca.presentation.models.event_flow.MutableEventFlow
import com.strayalphaca.presentation.models.event_flow.asEventFlow
import com.strayalphaca.travel_diary.core.presentation.logger.UserEventLogger
import com.strayalphaca.travel_diary.core.presentation.logger.UserLogEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupPasswordViewModel @Inject constructor(
    private val useCaseSignup: UseCaseSignup,
    private val userEventLogger: UserEventLogger
) : ViewModel() {
    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()

    private val _signupState = MutableStateFlow<SignupPasswordViewState>(SignupPasswordViewState.InputPasswordStep)
    val signupState = _signupState.asStateFlow()

    private val _errorMessage = MutableStateFlow("")
    val errorMessage = _errorMessage.asStateFlow()

    private val _signUpSuccessEvent = MutableEventFlow<Boolean>()
    val signupSuccessEvent = _signUpSuccessEvent.asEventFlow()

    fun inputPassword(password : String) {
        _password.value = password
    }

    fun trySignUp() {
        viewModelScope.launch {
            _signupState.value = SignupPasswordViewState.OngoingSignupStep

            val email = SignupData.getInstance().getEmail()
            val response = useCaseSignup(email = email, password = password.value)

            _signupState.value = SignupPasswordViewState.InputPasswordStep

            if (response is BaseResponse.Success<*>) {
                SignupData.clearInstance()
                userEventLogger.log(UserLogEvent.Signup)
                _signUpSuccessEvent.emit(true)
            }
            if (response is BaseResponse.Failure) {
                _errorMessage.value = response.errorMessage
            }
        }
    }
}

sealed class SignupPasswordViewState(
    val passwordTextField : EditTextState,
    val bottomButtonState : BaseButtonState,
) {
    object InputPasswordStep : SignupPasswordViewState(
        passwordTextField = EditTextState.ACTIVE,
        bottomButtonState = BaseButtonState.ACTIVE
    )

    object OngoingSignupStep : SignupPasswordViewState(
        passwordTextField = EditTextState.INACTIVE,
        bottomButtonState = BaseButtonState.INACTIVE
    )
}