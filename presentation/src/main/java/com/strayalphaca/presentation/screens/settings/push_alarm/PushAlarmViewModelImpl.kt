package com.strayalphaca.presentation.screens.settings.push_alarm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.strayalphaca.presentation.models.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PushAlarmViewModelImpl @Inject constructor(
) : PushAlarmViewModel, ViewModel() {
    // 초기값은 local data source 에서 가져와야 한다.
    private val _usePushAlarm = MutableStateFlow(false)
    override val usePushAlarm: StateFlow<Boolean> = _usePushAlarm.asStateFlow()

    private val _pushAlarmMinute = MutableStateFlow(20 * 60 + 30)
    override val pushAlarmMinute: StateFlow<Int> = _pushAlarmMinute.asStateFlow()

    private val _clickTarget = MutableStateFlow(Route.pushAlarmTargetList.last())
    override val clickTarget: StateFlow<Route?> = _clickTarget.asStateFlow()

    override fun setUsePushAlarm(use: Boolean) {
        viewModelScope.launch {
            _usePushAlarm.value = use
        }
    }

    override fun setPushAlarmTime(hour: Int, minute: Int) {
        viewModelScope.launch {
            _pushAlarmMinute.value = hour * 60 + minute
        }
    }

    override fun setPushAlarmClickTarget(targetLink: Route) {
        viewModelScope.launch {
            _clickTarget.value = targetLink
        }
    }
}