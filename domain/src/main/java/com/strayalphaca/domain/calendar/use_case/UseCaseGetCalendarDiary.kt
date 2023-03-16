package com.strayalphaca.domain.calendar.use_case

import com.strayalphaca.domain.all.getDayAmountOfMonth
import com.strayalphaca.domain.calendar.model.DiaryInCalendar
import com.strayalphaca.domain.calendar.repository.CalendarRepository
import com.strayalphaca.domain.model.BaseResponse
import javax.inject.Inject

class UseCaseGetCalendarDiary @Inject constructor(
    private val calendarRepository: CalendarRepository
) {
    suspend operator fun invoke(year : Int, month : Int) : BaseResponse<List<DiaryInCalendar?>> {
        val response = calendarRepository.getDiaryData(year, month)
        if (response !is BaseResponse.Success) return response

        val rawData = response.data
        if (rawData.isEmpty()) return response.copy(data = listOf())

        var count = 0
        val dataList = (0 until getDayAmountOfMonth(year, month)).toList().map {
            return@map if (count < rawData.size && rawData[count].day == it) {
                rawData[count++]
            } else {
                null
            }
        }

        return BaseResponse.Success(dataList)
    }
}