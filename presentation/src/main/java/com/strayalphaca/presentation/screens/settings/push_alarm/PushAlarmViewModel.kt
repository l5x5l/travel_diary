package com.strayalphaca.presentation.screens.settings.push_alarm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.strayalphaca.presentation.alarm.TrailyAlarmManager
import com.strayalphaca.presentation.models.Route
import com.strayalphaca.travel_diary.core.presentation.logger.UserEventLogger
import com.strayalphaca.travel_diary.core.presentation.logger.UserLogEvent
import com.strayalphaca.travel_diary.domain.alarm.model.AlarmInfo
import com.strayalphaca.travel_diary.domain.alarm.usecase.UseCaseGetAlarmInfo
import com.strayalphaca.travel_diary.domain.alarm.usecase.UseCaseSetAlarmInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PushAlarmViewModel @Inject constructor(
    private val alarmManager: TrailyAlarmManager,
    private val useCaseGetAlarmInfo: UseCaseGetAlarmInfo,
    private val useCaseSetAlarmInfo: UseCaseSetAlarmInfo,
    private val userEventLogger: UserEventLogger
) : ViewModel() {
    private val _usePushAlarm = MutableStateFlow(false)
    val usePushAlarm: StateFlow<Boolean> = _usePushAlarm.asStateFlow()

    private val _pushAlarmMinute = MutableStateFlow(20 * 60 + 30)
    val pushAlarmMinute: StateFlow<Int> = _pushAlarmMinute.asStateFlow()

    private val _clickTarget = MutableStateFlow(Route.pushAlarmTargetList.last())
    val clickTarget: StateFlow<Route?> = _clickTarget.asStateFlow()

    private val _requestPermissionSettingAction = MutableStateFlow<String?>(null)
    val requestPermissionSettingAction  =_requestPermissionSettingAction.asStateFlow()

    init {
        viewModelScope.launch {
            useCaseGetAlarmInfo.invoke().collectLatest { alarmInfo ->
                _pushAlarmMinute.value = alarmInfo.hour * 60 + alarmInfo.minute
                _usePushAlarm.value = alarmInfo.alarmOn
                _clickTarget.value = Route.getInstanceByUriString(alarmInfo.routeLink)
            }
        }
    }

    fun setUsePushAlarm(use: Boolean) {
        _usePushAlarm.value = use
        if (use) {
            applyPushAlarm()
        } else {
            alarmManager.cancelAlarm()
            setAlarmInfo()
        }
    }

    fun setPushAlarmTime(hour: Int, minute: Int) {
        _pushAlarmMinute.value = hour * 60 + minute
        applyPushAlarm()
    }

    fun setPushAlarmClickTarget(targetLink: Route) {
        _clickTarget.value = targetLink
        applyPushAlarm()
    }

    private fun applyPushAlarm() {
        val hour = pushAlarmMinute.value / 60
        val minute = pushAlarmMinute.value % 60
        val screenDeepLink = clickTarget.value?.uri
        alarmManager.setAlarm(
            hour = hour, minute = minute, screenDeepLink = screenDeepLink
        )
        userEventLogger.log(
            UserLogEvent.SetPushAlarm(
                hour = hour, minute = minute, route = screenDeepLink ?: "null"
            )
        )
        setAlarmInfo()
    }

    private fun setAlarmInfo() {
        val alarmInfo = AlarmInfo(
            alarmOn = usePushAlarm.value,
            hour = pushAlarmMinute.value / 60,
            minute = pushAlarmMinute.value % 60,
            routeLink = clickTarget.value?.uri
        )
        viewModelScope.launch {
            useCaseSetAlarmInfo(alarmInfo)
        }
    }

    fun showPermissionRequestDialog(action : String) {
        _requestPermissionSettingAction.value = action
    }

    fun dismissPermissionRequestDialog() {
        _requestPermissionSettingAction.value = null
    }
}