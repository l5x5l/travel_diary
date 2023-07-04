package com.strayalphaca.presentation.screens.settings.push_alarm

import androidx.lifecycle.ViewModel
import com.strayalphaca.presentation.alarm.TrailyAlarmManager
import com.strayalphaca.presentation.models.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class PushAlarmViewModel @Inject constructor(
    private val alarmManager: TrailyAlarmManager
) : ViewModel() {
    // 초기값은 local data source 에서 가져와야 한다.
    private val _usePushAlarm = MutableStateFlow(false)
     val usePushAlarm: StateFlow<Boolean> = _usePushAlarm.asStateFlow()

    private val _pushAlarmMinute = MutableStateFlow(20 * 60 + 30)
     val pushAlarmMinute: StateFlow<Int> = _pushAlarmMinute.asStateFlow()

    private val _clickTarget = MutableStateFlow(Route.pushAlarmTargetList.last())
     val clickTarget: StateFlow<Route?> = _clickTarget.asStateFlow()


     fun setUsePushAlarm(use: Boolean) {
        _usePushAlarm.value = use
        if (use) {
            applyPushAlarm()
        } else {
            alarmManager.setAlarmOff()
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
        alarmManager.setAlarm(pushAlarmMinute.value / 60, pushAlarmMinute.value % 60, clickTarget.value?.uri)
    }
}