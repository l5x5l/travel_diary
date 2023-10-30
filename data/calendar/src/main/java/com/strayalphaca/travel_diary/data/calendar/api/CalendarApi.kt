package com.strayalphaca.travel_diary.data.calendar.api

import com.strayalphaca.data.all.model.ListResponseData
import com.strayalphaca.travel_diary.data.calendar.models.CalendarDiaryDto
import retrofit2.Response
import retrofit2.http.GET

interface CalendarApi {
    @GET("records/calendar")
    suspend fun getDiaryOfMonth(year : Int, month : Int) : Response<ListResponseData<CalendarDiaryDto>>

    @GET("records/exists")
    suspend fun checkRecordExists(recordDate : String) : Response<Nothing>
}