package com.strayalphaca.travel_diary.map.usecase

import com.strayalphaca.travel_diary.map.repository.MapRepository
import javax.inject.Inject

class UseCaseRefreshCachedMap @Inject constructor(
    private val repository : MapRepository
) {
    suspend operator fun invoke(id : String) {
        repository.refreshIfContainDiary(diaryId = id)
    }
}