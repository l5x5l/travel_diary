package com.strayalphaca.travel_diary.data.calendar.data_store

import com.strayalphaca.travel_diary.domain.calendar.model.DiaryInCalendar
import com.strayalphaca.travel_diary.domain.calendar.model.MonthCalendar
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CalendarDataStoreImpl @Inject constructor() : CalendarDataStore {
    private val monthCalendarData = MutableStateFlow(MonthCalendar.getInstanceFromCalendar())

    override suspend fun setCalendarData(year : Int, month : Int, dataList: List<DiaryInCalendar>) {
        monthCalendarData.value = MonthCalendar(
            year = year,
            month = month,
            diaryList = dataList
        )
    }

    override fun getCalendarData(): Flow<MonthCalendar> {
        return monthCalendarData
    }

    override suspend fun updateCalendarCell(data: DiaryInCalendar) {
        val diaryList = monthCalendarData.value.diaryList
        val target : DiaryInCalendar = diaryList.find { it.id == data.id } ?: return
        val dataList : List<DiaryInCalendar> = diaryList.map {
            if (it.id == target.id) target else it
        }
        monthCalendarData.value = monthCalendarData.value.copy(diaryList = dataList)
    }

    override suspend fun deleteCalendarCell(data: DiaryInCalendar) {
        val dataList : List<DiaryInCalendar> = monthCalendarData.value.diaryList.filter { it.id != data.id }
        monthCalendarData.value = monthCalendarData.value.copy(diaryList = dataList)
    }

    override suspend fun clearCalendarData() {
        monthCalendarData.value = monthCalendarData.value.copy(diaryList = emptyList())
    }
}