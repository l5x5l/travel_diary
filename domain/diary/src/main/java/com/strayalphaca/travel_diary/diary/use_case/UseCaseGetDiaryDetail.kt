package com.strayalphaca.travel_diary.diary.use_case

import com.strayalphaca.travel_diary.diary.model.DiaryDetail
import com.strayalphaca.travel_diary.diary.repository.DiaryRepository
import com.strayalpaca.travel_diary.core.domain.model.BaseResponse
import javax.inject.Inject

class UseCaseGetDiaryDetail @Inject constructor(
    private val repository: DiaryRepository
) {
    suspend operator fun invoke(id : String) : BaseResponse<DiaryDetail> {
        return repository.getDiaryDetail(id)
    }
}