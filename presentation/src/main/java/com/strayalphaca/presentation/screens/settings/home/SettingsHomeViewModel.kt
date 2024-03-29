package com.strayalphaca.presentation.screens.settings.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.strayalpaca.travel_diary.domain.lock.use_case.UseCaseRemovePassword
import com.strayalphaca.presentation.alarm.TrailyAlarmManager
import com.strayalphaca.presentation.models.event_flow.MutableEventFlow
import com.strayalphaca.presentation.models.event_flow.asEventFlow
import com.strayalphaca.travel_diary.core.presentation.logger.UserEventLogger
import com.strayalphaca.travel_diary.core.presentation.logger.UserLogEvent
import com.strayalphaca.travel_diary.domain.alarm.model.AlarmInfo
import com.strayalphaca.travel_diary.domain.alarm.usecase.UseCaseSetAlarmInfo
import com.strayalphaca.travel_diary.domain.auth.usecase.UseCaseClearToken
import com.strayalphaca.travel_diary.domain.auth.usecase.UseCaseGetAccessToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsHomeViewModel @Inject constructor(
    private val useCaseGetAccessToken: UseCaseGetAccessToken,
    private val useCaseClearToken: UseCaseClearToken,
    private val alarmManager: TrailyAlarmManager,
    private val useCaseSetAlarmInfo: UseCaseSetAlarmInfo,
    private val userEventLogger: UserEventLogger,
    private val useCaseRemoveLockPassword: UseCaseRemovePassword
) : ViewModel() {
    private val _isLogin = MutableStateFlow(false)
    val isLogin = _isLogin.asStateFlow()

    private val _logoutCheckDialogVisible = MutableStateFlow(false)
    val logoutCheckDialogVisible = _logoutCheckDialogVisible.asStateFlow()

    private val _navigateToIntroEvent = MutableEventFlow<Boolean>()
    val navigateToIntroEvent = _navigateToIntroEvent.asEventFlow()

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

            useCaseSetAlarmInfo(AlarmInfo(alarmOn = false, hour = 20, minute = 0))
            useCaseRemoveLockPassword()
            alarmManager.cancelAlarm()

            userEventLogger.log(UserLogEvent.Logout)
            _navigateToIntroEvent.emit(true)
        }
    }

}