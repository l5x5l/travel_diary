package com.strayalphaca.travel_diary.data.alarm.repository

import com.strayalphaca.travel_diary.data.alarm.data_source.AlarmDataStore
import com.strayalphaca.travel_diary.domain.alarm.AlarmRepository
import com.strayalphaca.travel_diary.domain.alarm.model.AlarmInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class AlarmRepositoryImpl @Inject constructor(
    private val alarmDataStore: AlarmDataStore
) : AlarmRepository {
    override suspend fun getAlarmInfo(): Flow<AlarmInfo> {
        return combine(
            alarmDataStore.getUseAlarm(),
            alarmDataStore.getAlarmHour(),
            alarmDataStore.getAlarmMinute(),
            alarmDataStore.getAlarmRoute()
        ) { useAlarm, hour, minute, route ->
            AlarmInfo(useAlarm, hour, minute, route)
        }
    }

    override suspend fun setAlarmInfo(alarmInfo: AlarmInfo) {
        alarmDataStore.setAlarmInfo(alarmInfo)
    }
}