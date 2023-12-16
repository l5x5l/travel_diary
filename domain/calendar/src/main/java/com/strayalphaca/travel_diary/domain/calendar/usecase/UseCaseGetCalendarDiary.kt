package com.strayalphaca.travel_diary.domain.calendar.usecase

import com.strayalphaca.travel_diary.domain.calendar.repository.CalendarRepository
import com.strayalpaca.travel_diary.core.domain.model.BaseResponse
import com.strayalphaca.travel_diary.domain.calendar.model.MonthCalendar
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UseCaseGetCalendarDiary @Inject constructor(
    private val calendarRepository: CalendarRepository
) {
    suspend operator fun invoke(year : Int, month : Int) : BaseResponse<Nothing> {
        val response = calendarRepository.loadDiaryData(year, month)
        if (response is BaseResponse.Failure) return response
        return BaseResponse.EmptySuccess
    }

    fun monthCalendarFlow() : Flow<MonthCalendar> {
        return calendarRepository.getMonthCalendarFlow()
    }
}