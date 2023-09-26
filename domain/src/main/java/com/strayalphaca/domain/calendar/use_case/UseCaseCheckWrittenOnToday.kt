package com.strayalphaca.domain.calendar.use_case

import com.strayalphaca.domain.calendar.repository.CalendarRepository
import com.strayalphaca.domain.model.BaseResponse
import javax.inject.Inject

class UseCaseCheckWrittenOnToday @Inject constructor(
    private val repository : CalendarRepository
) {
    suspend operator fun invoke() : BaseResponse<Boolean> {
        return repository.checkWrittenOnToday()
    }
}