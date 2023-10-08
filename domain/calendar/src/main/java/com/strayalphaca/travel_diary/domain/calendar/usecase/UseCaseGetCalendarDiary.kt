package com.strayalphaca.travel_diary.domain.calendar.usecase

import com.strayalphaca.travel_diary.domain.calendar.model.DiaryInCalendar
import com.strayalphaca.travel_diary.domain.calendar.repository.CalendarRepository
import com.strayalphaca.domain.model.BaseResponse
import com.strayalphaca.travel_diary.domain.calendar.utils.fillEmptyCellToCalendarData
import javax.inject.Inject

class UseCaseGetCalendarDiary @Inject constructor(
    private val calendarRepository: CalendarRepository
) {
    suspend operator fun invoke(year : Int, month : Int) : BaseResponse<List<DiaryInCalendar?>> {
        val response = calendarRepository.getDiaryData(year, month)
        if (response !is BaseResponse.Success) return response

        val rawData = response.data
        if (rawData.isEmpty()) return response.copy(data = listOf())

        val dataList = fillEmptyCellToCalendarData(year, month, rawData)

        return BaseResponse.Success(dataList)
    }
}