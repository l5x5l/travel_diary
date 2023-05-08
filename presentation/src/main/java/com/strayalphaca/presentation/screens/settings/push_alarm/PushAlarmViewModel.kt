package com.strayalphaca.presentation.screens.settings.push_alarm

import com.strayalphaca.presentation.models.Route
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface PushAlarmViewModel {
    val usePushAlarm : StateFlow<Boolean>
    val pushAlarmMinute : StateFlow<Int>
    val clickTarget : StateFlow<Route?>
    fun setUsePushAlarm(use : Boolean)
    fun setPushAlarmTime(hour : Int, minute : Int)
    fun setPushAlarmClickTarget(targetLink : Route)

    companion object {
        val TestPushAlarmViewModel = object : PushAlarmViewModel {
            override val usePushAlarm: StateFlow<Boolean> = MutableStateFlow(false)

            override val pushAlarmMinute: StateFlow<Int> = MutableStateFlow(20 * 60 + 30)

            override val clickTarget: StateFlow<Route?> = MutableStateFlow(null)

            override fun setUsePushAlarm(use: Boolean) {}

            override fun setPushAlarmTime(hour: Int, minute: Int) {}

            override fun setPushAlarmClickTarget(targetLink: Route) {}
        }
    }
}