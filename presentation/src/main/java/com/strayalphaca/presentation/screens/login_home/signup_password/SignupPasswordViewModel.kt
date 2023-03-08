package com.strayalphaca.presentation.screens.login_home.signup_password

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SignupPasswordViewModel : ViewModel() {
    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()

    fun inputPassword(password : String) {
        _password.value = password
    }
}