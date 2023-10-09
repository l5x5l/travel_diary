package com.strayalphaca.travel_diary.domain.calendar.usecase

import com.strayalphaca.travel_diary.domain.calendar.model.DiaryInCalendar
import com.strayalphaca.travel_diary.domain.calendar.repository.CalendarRepository
import javax.inject.Inject

class UseCaseHandleCachedCalendarDiary @Inject constructor(
    private val repository : CalendarRepository
) {
    suspend fun update(diaryInCalendar: DiaryInCalendar) {
        repository.updateCachedDiaryInCalendar(diaryInCalendar)
    }

    suspend fun delete(id : String) {
        repository.deleteCachedDiaryInCalendar(id)
    }

    suspend fun add(diaryInCalendar: DiaryInCalendar) {
        repository.addCachedDiaryInCalendar(diaryInCalendar)
    }
}