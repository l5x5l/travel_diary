package com.strayalphaca.travel_diary.domain.calendar.usecase

import com.strayalphaca.travel_diary.domain.calendar.repository.CalendarRepository
import com.strayalpaca.travel_diary.core.domain.model.BaseResponse
import javax.inject.Inject

class UseCaseCheckWrittenOnToday @Inject constructor(
    private val repository : CalendarRepository
) {
    suspend operator fun invoke() : BaseResponse<Boolean> {
        return repository.checkWrittenOnToday()
    }
}