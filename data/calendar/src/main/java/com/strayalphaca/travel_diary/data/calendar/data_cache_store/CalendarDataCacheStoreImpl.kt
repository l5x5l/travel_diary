package com.strayalphaca.travel_diary.data.calendar.data_cache_store

import com.strayalphaca.travel_diary.domain.calendar.model.DiaryInCalendar
import com.strayalphaca.travel_diary.domain.calendar.model.MonthCalendar
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CalendarDataCacheStoreImpl @Inject constructor() : CalendarDataCacheStore {
    private val monthCalendarData = MutableStateFlow(MonthCalendar.getInstanceFromCalendar())

    override suspend fun setCalendarData(year : Int, month : Int, dataList: List<DiaryInCalendar>) {
        monthCalendarData.value = MonthCalendar(
            year = year,
            month = month,
            diaryList = dataList.sortedBy { it.day }
        )
    }

    override fun getCalendarData(): Flow<MonthCalendar> {
        return monthCalendarData
    }

    override suspend fun addCalendarCell(data: DiaryInCalendar) {
        val diaryList : List<DiaryInCalendar> = (monthCalendarData.value.diaryList + data).sortedBy { it.day }
        monthCalendarData.value = monthCalendarData.value.copy(diaryList = diaryList)
    }

    override suspend fun updateCalendarCell(data: DiaryInCalendar) {
        val diaryList : List<DiaryInCalendar> = monthCalendarData.value.diaryList
        val target : DiaryInCalendar = diaryList.find { it.id == data.id } ?: return
        val dataList : List<DiaryInCalendar> = diaryList.map {
            if (it.id == target.id) data else it
        }.sortedBy { it.day }
        monthCalendarData.value = monthCalendarData.value.copy(diaryList = dataList)
    }

    override suspend fun deleteCalendarCell(id : String) {
        val dataList : List<DiaryInCalendar> = monthCalendarData.value.diaryList.filter { it.id != id }
        monthCalendarData.value = monthCalendarData.value.copy(diaryList = dataList)
    }

    override suspend fun clearCalendarData() {
        monthCalendarData.value = monthCalendarData.value.copy(diaryList = emptyList())
    }
}