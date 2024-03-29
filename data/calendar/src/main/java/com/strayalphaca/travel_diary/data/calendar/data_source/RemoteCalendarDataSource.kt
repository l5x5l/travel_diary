package com.strayalphaca.travel_diary.data.calendar.data_source

import com.strayalphaca.travel_diary.core.data.utils.responseToBaseResponseWithMapping
import com.strayalpaca.travel_diary.core.domain.model.DiaryDate
import com.strayalpaca.travel_diary.core.domain.model.BaseResponse
import com.strayalphaca.travel_diary.data.calendar.api.CalendarApi
import com.strayalphaca.travel_diary.data.calendar.models.CalendarDiaryDto
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteCalendarDataSource @Inject constructor(
    retrofit: Retrofit
) : CalendarDataSource {
    private val calendarRetrofit = retrofit.create(CalendarApi::class.java)

    override suspend fun getDiaryData(year: Int, month: Int): BaseResponse<List<CalendarDiaryDto>> {
        val response = calendarRetrofit.getDiaryOfMonth(year, month)
        return responseToBaseResponseWithMapping(response) { calendarDiaryDtoList ->
            calendarDiaryDtoList.data.map { it.apply { dateStringFormat = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'" } }
        }
    }

    override suspend fun checkWrittenOnToday(): BaseResponse<Boolean> {
        val todayDateString = DiaryDate.getInstanceFromCalendar()
        val response = calendarRetrofit.checkRecordExists(todayDateString.toString())

        val baseResponse = when {
            response.isSuccessful && response.code() == 200 -> {
                BaseResponse.Success(data = true)
            }
            response.code() == 409 -> {
                BaseResponse.Success(data = false)
            }
            else -> {
                BaseResponse.Failure(errorCode = response.code(), errorMessage = response.message())
            }
        }
        return baseResponse
    }

}