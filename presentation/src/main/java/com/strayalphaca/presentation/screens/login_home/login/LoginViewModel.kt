package com.strayalphaca.presentation.screens.login_home.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.strayalphaca.travel_diary.domain.login.use_case.UseCaseLogin
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val useCaseLogin: UseCaseLogin
) : ViewModel() {
    private val _email = MutableStateFlow("")
    val email = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()

    private val _networkLoading = MutableStateFlow(false)
    val networkLoading = _networkLoading.asStateFlow()

    fun inputEmail(email : String) {
        _email.value = email
    }

    fun inputPassword(password: String) {
        _password.value = password
    }

    fun tryLogin() {
        viewModelScope.launch {
            _networkLoading.value = true
            val response = useCaseLogin(email = email.value, password = password.value)
            _networkLoading.value = false
        }
    }
}