package com.strayalphaca.travel_diary.domain.alarm.usecase

import com.strayalphaca.travel_diary.domain.alarm.AlarmRepository
import javax.inject.Inject

class UseCaseGetAlarmInfo @Inject constructor(
    private val repository: AlarmRepository
){
    suspend operator fun invoke() = repository.getAlarmInfo()
}