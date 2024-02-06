package com.strayalphaca.travel_diary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.strayalpaca.travel_diary.domain.lock.use_case.UseCaseUsePassword
import com.strayalphaca.presentation.models.deeplink_handler.NotificationDeepLinkHandler
import com.strayalphaca.presentation.models.event_flow.MutableEventFlow
import com.strayalphaca.presentation.models.event_flow.asEventFlow
import com.strayalphaca.travel_diary.domain.auth.repository.AuthRepository
import com.strayalphaca.travel_diary.domain.auth.usecase.UseCaseSaveToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RootViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val deepLinkHandler: NotificationDeepLinkHandler,
    private val useCaseUsePassword: UseCaseUsePassword,
    private val useCaseSaveToken: UseCaseSaveToken
) : ViewModel() {
    val invalidRefreshToken = authRepository.invalidRefreshToken()

    private val _showLockScreen = MutableEventFlow<Boolean>()
    val showLockScreen = _showLockScreen.asEventFlow()

    fun handleLink(deepLink : String) {
        viewModelScope.launch {
            deepLinkHandler.setDeepLinkEvent(deepLink)
        }
    }

    fun callLockScreen() {
        viewModelScope.launch {
            if (authRepository.getAccessToken() == null || !useCaseUsePassword())
                return@launch

            _showLockScreen.emit(true)
        }
    }

    fun setTokenToLocal() {
        viewModelScope.launch {
            useCaseSaveToken("local")
        }
    }
}