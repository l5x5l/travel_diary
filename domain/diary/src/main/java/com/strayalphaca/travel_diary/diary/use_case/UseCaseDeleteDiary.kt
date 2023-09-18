package com.strayalphaca.travel_diary.diary.use_case

import com.strayalphaca.travel_diary.diary.repository.DiaryRepository
import javax.inject.Inject

class UseCaseDeleteDiary @Inject constructor(
    private val repository : DiaryRepository
) {
    suspend operator fun invoke(diaryId : String) = repository.deleteDiary(diaryId)
}