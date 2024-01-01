package com.strayalphaca.presentation.screens.login_home.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.strayalpaca.travel_diary.core.domain.model.BaseResponse
import com.strayalphaca.presentation.models.event_flow.MutableEventFlow
import com.strayalphaca.presentation.models.event_flow.asEventFlow
import com.strayalphaca.travel_diary.core.presentation.logger.UserEventLogger
import com.strayalphaca.travel_diary.core.presentation.logger.UserLogEvent
import com.strayalphaca.travel_diary.domain.auth.usecase.UseCaseSaveToken
import com.strayalphaca.travel_diary.domain.login.model.Tokens
import com.strayalphaca.travel_diary.domain.login.use_case.UseCaseLogin
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val useCaseLogin: UseCaseLogin,
    private val useCaseSaveToken: UseCaseSaveToken,
    private val userEventLogger: UserEventLogger
) : ViewModel() {
    private val _email = MutableStateFlow("")
    val email = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()

    private val _networkLoading = MutableStateFlow(false)
    val networkLoading = _networkLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow("")
    val errorMessage = _errorMessage.asStateFlow()

    private val _loginSuccess = MutableEventFlow<Boolean>()
    val loginSuccess = _loginSuccess.asEventFlow()

    fun inputEmail(email : String) {
        _email.value = email
    }

    fun inputPassword(password: String) {
        _password.value = password
    }

    fun tryLogin() {
        viewModelScope.launch {
            _networkLoading.value = true
            _errorMessage.value = ""
            val response = useCaseLogin(email = email.value, password = password.value)
            if (response is BaseResponse.Failure) {
                _errorMessage.value = response.errorMessage
            } else {
                response as BaseResponse.Success<Tokens>
                useCaseSaveToken(response.data.accessToken, response.data.refreshToken)
                userEventLogger.log(UserLogEvent.Login)
                _loginSuccess.emit(true)
            }
            _networkLoading.value = false
        }
    }
}