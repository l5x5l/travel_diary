package com.strayalphaca.presentation.screens.settings.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.strayalphaca.travel_diary.domain.auth.usecase.UseCaseClearToken
import com.strayalphaca.travel_diary.domain.auth.usecase.UseCaseGetAccessToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsHomeViewModel @Inject constructor(
    private val useCaseGetAccessToken: UseCaseGetAccessToken,
    private val useCaseClearToken: UseCaseClearToken
) : ViewModel() {
    private val _isLogin = MutableStateFlow(false)
    val isLogin = _isLogin.asStateFlow()

    private val _logoutCheckDialogVisible = MutableStateFlow(false)
    val logoutCheckDialogVisible = _logoutCheckDialogVisible.asStateFlow()

    private val _navigateToIntroEvent = MutableSharedFlow<Boolean>()
    val navigateToIntroEvent = _navigateToIntroEvent.asSharedFlow()

    fun showLogoutCheckDialog() {
        _logoutCheckDialogVisible.value = true
    }

    fun hideLogoutCheckDialog() {
        _logoutCheckDialogVisible.value = false
    }

    fun checkIsLogin() {
        viewModelScope.launch {
            val token = useCaseGetAccessToken()
            _isLogin.value = (token != null)
        }
    }

    fun logout() {
        viewModelScope.launch {
            useCaseClearToken()
            _navigateToIntroEvent.emit(true)
        }
    }

}