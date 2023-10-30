package com.strayalphaca.travel_diary.data.calendar.data_source

import com.strayalphaca.data.all.utils.responseToBaseResponseWithMapping
import com.strayalphaca.data.all.utils.voidResponseToBaseResponse
import com.strayalphaca.domain.all.DiaryDate
import com.strayalphaca.domain.model.BaseResponse
import com.strayalphaca.travel_diary.data.calendar.api.CalendarApi
import com.strayalphaca.travel_diary.data.calendar.models.CalendarDiaryDto
import retrofit2.Retrofit
import javax.inject.Inject

class RemoteCalendarDataSource @Inject constructor(
    retrofit: Retrofit
) : CalendarDataSource {
    private val calendarRetrofit = retrofit.create(CalendarApi::class.java)

    override suspend fun getDiaryData(year: Int, month: Int): BaseResponse<List<CalendarDiaryDto>> {
        val response = calendarRetrofit.getDiaryOfMonth(year, month)
        return responseToBaseResponseWithMapping(response) {it.data}
    }

    override suspend fun checkWrittenOnToday(): BaseResponse<Boolean> {
        val todayDateString = DiaryDate.getInstanceFromCalendar()
        val response = calendarRetrofit.checkRecordExists(todayDateString.toString())
        return voidResponseToBaseResponse(response)
    }

}