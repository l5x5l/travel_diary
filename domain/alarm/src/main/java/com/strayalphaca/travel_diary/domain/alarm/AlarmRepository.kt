package com.strayalphaca.travel_diary.domain.alarm

import com.strayalphaca.travel_diary.domain.alarm.model.AlarmInfo
import kotlinx.coroutines.flow.Flow

interface AlarmRepository {
    suspend fun getAlarmInfo() : Flow<AlarmInfo>
    suspend fun setAlarmInfo(alarmInfo: AlarmInfo)
}