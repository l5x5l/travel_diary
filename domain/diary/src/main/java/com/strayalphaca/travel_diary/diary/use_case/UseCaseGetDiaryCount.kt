package com.strayalphaca.travel_diary.diary.use_case

import com.strayalphaca.domain.model.BaseResponse
import com.strayalphaca.travel_diary.diary.repository.DiaryRepository
import javax.inject.Inject

class UseCaseGetDiaryCount @Inject constructor(
    private val repository : DiaryRepository
) {
    suspend operator fun invoke() : BaseResponse<Int> {
        return repository.getDiaryCount()
    }
}