package com.strayalphaca.travel_diary.data.calendar.data_store

import com.strayalphaca.travel_diary.domain.calendar.model.DiaryInCalendar
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CalendarDataStoreImpl @Inject constructor() : CalendarDataStore {
    private val calendarData = MutableStateFlow<List<DiaryInCalendar>>(emptyList())

    override suspend fun setCalendarData(dataList: List<DiaryInCalendar>) {
        calendarData.value = dataList
    }

    override suspend fun getCalendarData(): List<DiaryInCalendar> {
        return calendarData.value
    }

    override suspend fun updateCalendarCell(data: DiaryInCalendar) {
        val target : DiaryInCalendar = calendarData.value.find { it.id == data.id } ?: return
        val dataList : List<DiaryInCalendar> = calendarData.value.map {
            if (it.id == target.id) target else it
        }
        calendarData.value = dataList
    }

    override suspend fun deleteCalendarCell(data: DiaryInCalendar) {
        val dataList : List<DiaryInCalendar> = calendarData.value.filter { it.id != data.id }
        calendarData.value = dataList
    }

    override suspend fun clearCalendarData() {
        calendarData.value = emptyList()
    }
}