package com.strayalphaca.domain.diary.use_case

import com.strayalphaca.domain.diary.model.DiaryModifyData
import com.strayalphaca.domain.diary.repository.DiaryRepository
import com.strayalphaca.domain.model.BaseResponse
import javax.inject.Inject

class UseCaseModifyDiary @Inject constructor(
    private val repository: DiaryRepository
) {
    suspend operator fun invoke(diaryModifyData: DiaryModifyData) : BaseResponse<Nothing> {
        return repository.modifyDiary(diaryModifyData)
    }
}