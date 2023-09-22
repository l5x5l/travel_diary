package com.strayalphaca.travel_diary.domain.alarm.usecase

import com.strayalphaca.travel_diary.domain.alarm.AlarmRepository
import com.strayalphaca.travel_diary.domain.alarm.model.AlarmInfo
import javax.inject.Inject

class UseCaseSetAlarmInfo @Inject constructor(
    private val repository: AlarmRepository
) {
    suspend operator fun invoke(alarmInfo : AlarmInfo) = repository.setAlarmInfo(alarmInfo)
}