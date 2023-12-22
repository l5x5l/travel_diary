package com.strayalphaca.travel_diary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.strayalphaca.presentation.models.deeplink_handler.NotificationDeepLinkHandler
import com.strayalphaca.travel_diary.domain.auth.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RootViewModel @Inject constructor(
    authRepository: AuthRepository,
    private val deepLinkHandler: NotificationDeepLinkHandler
) : ViewModel() {
    val invalidRefreshToken = authRepository.invalidRefreshToken()

    fun handleLink(deepLink : String) {
        viewModelScope.launch {
            deepLinkHandler.setDeepLinkEvent(deepLink)
        }
    }
}