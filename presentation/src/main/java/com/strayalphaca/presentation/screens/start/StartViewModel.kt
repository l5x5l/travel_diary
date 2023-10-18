package com.strayalphaca.presentation.screens.start

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.strayalphaca.travel_diary.domain.auth.usecase.UseCaseGetAccessToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StartViewModel @Inject constructor(
    private val useCaseGetAccessToken: UseCaseGetAccessToken
) : ViewModel() {

    private val _navigationEvent = MutableSharedFlow<StartScreenNavDestination>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    init {
        checkHasAccessToken()
    }

    private fun checkHasAccessToken() {
        viewModelScope.launch {
            val accessToken = useCaseGetAccessToken()
            delay(1500L)
            if (accessToken == null) {
                _navigationEvent.emit(StartScreenNavDestination.LOGIN)
            } else {
                _navigationEvent.emit(StartScreenNavDestination.HOME)
            }
        }
    }
}

enum class StartScreenNavDestination {
    LOGIN, HOME
}