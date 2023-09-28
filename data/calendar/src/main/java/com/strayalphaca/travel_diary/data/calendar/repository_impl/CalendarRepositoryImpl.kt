package com.strayalphaca.travel_diary.data.calendar.repository_impl

import com.strayalphaca.travel_diary.data.calendar.utils.diaryDtoListToDiaryInCalendarList
import com.strayalphaca.travel_diary.data.calendar.data_source.CalendarDataSource
import com.strayalphaca.data.all.utils.mapBaseResponse
import com.strayalphaca.travel_diary.domain.calendar.model.DiaryInCalendar
import com.strayalphaca.travel_diary.domain.calendar.repository.CalendarRepository
import com.strayalphaca.domain.model.BaseResponse
import javax.inject.Inject

class CalendarRepositoryImpl @Inject constructor(
    private val calendarDataSource: CalendarDataSource
) : CalendarRepository {
    override suspend fun getDiaryData(year: Int, month: Int): BaseResponse<List<DiaryInCalendar>> {
        val data = calendarDataSource.getDiaryData(year, month)
        return mapBaseResponse(data, ::diaryDtoListToDiaryInCalendarList)
    }

    override suspend fun checkWrittenOnToday(): BaseResponse<Boolean> {
        return calendarDataSource.checkWrittenOnToday()
    }
}