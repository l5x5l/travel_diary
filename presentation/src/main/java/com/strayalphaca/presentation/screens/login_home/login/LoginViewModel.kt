package com.strayalphaca.presentation.screens.login_home.login

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class LoginViewModel : ViewModel() {
    private val _email = MutableStateFlow("")
    val email = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()

    fun inputEmail(email : String) {
        _email.value = email
    }

    fun inputPassword(password: String) {
        _password.value = password
    }
}