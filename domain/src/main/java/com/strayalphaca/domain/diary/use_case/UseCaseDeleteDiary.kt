package com.strayalphaca.domain.diary.use_case

import com.strayalphaca.domain.diary.repository.DiaryRepository
import javax.inject.Inject

class UseCaseDeleteDiary @Inject constructor(
    private val repository : DiaryRepository
) {
    suspend operator fun invoke(diaryId : String) = repository.deleteDiary(diaryId)
}