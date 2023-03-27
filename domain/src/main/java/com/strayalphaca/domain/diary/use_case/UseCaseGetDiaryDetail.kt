package com.strayalphaca.domain.diary.use_case

import com.strayalphaca.domain.diary.model.DiaryDetail
import com.strayalphaca.domain.diary.repository.DiaryRepository
import com.strayalphaca.domain.model.BaseResponse
import javax.inject.Inject

class UseCaseGetDiaryDetail @Inject constructor(
    private val repository: DiaryRepository
) {
    suspend operator fun invoke(id : String) : BaseResponse<DiaryDetail> {
        return repository.getDiaryDetail(id)
    }
}