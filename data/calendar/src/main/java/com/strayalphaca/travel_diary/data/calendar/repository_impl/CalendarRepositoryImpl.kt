package com.strayalphaca.travel_diary.data.calendar.repository_impl

import com.strayalphaca.travel_diary.data.calendar.data_source.CalendarDataSource
import com.strayalphaca.travel_diary.domain.calendar.repository.CalendarRepository
import com.strayalpaca.travel_diary.core.domain.model.BaseResponse
import com.strayalphaca.travel_diary.data.calendar.data_cache_store.CalendarDataCacheStore
import com.strayalphaca.travel_diary.domain.calendar.model.DiaryInCalendar
import com.strayalphaca.travel_diary.domain.calendar.model.MonthCalendar
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CalendarRepositoryImpl @Inject constructor(
    private val calendarDataSource: CalendarDataSource,
    private val calendarDataCacheStore: CalendarDataCacheStore
) : CalendarRepository {
    override suspend fun loadDiaryData(year: Int, month: Int): BaseResponse<Nothing> {
        val response = calendarDataSource.getDiaryData(year, month)
        return if (response is BaseResponse.Success) {
            calendarDataCacheStore.setCalendarData(
                year = year,
                month = month,
                dataList = response.data.map { it.toDiaryInCalendar() }
            )
            BaseResponse.EmptySuccess
        } else {
            response as BaseResponse.Failure
        }
    }

    override suspend fun checkWrittenOnToday(): BaseResponse<Boolean> {
        return calendarDataSource.checkWrittenOnToday()
    }

    override fun getMonthCalendarFlow(): Flow<MonthCalendar> {
        return calendarDataCacheStore.getCalendarData()
    }

    override suspend fun updateCachedDiaryInCalendar(diaryInCalendar: DiaryInCalendar) {
        calendarDataCacheStore.updateCalendarCell(diaryInCalendar)
    }

    override suspend fun deleteCachedDiaryInCalendar(id : String) {
        calendarDataCacheStore.deleteCalendarCell(id)
    }

    override suspend fun addCachedDiaryInCalendar(diaryInCalendar: DiaryInCalendar) {
        calendarDataCacheStore.addCalendarCell(diaryInCalendar)
    }
}