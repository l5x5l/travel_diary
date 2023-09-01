package com.strayalphaca.domain.diary.use_case

import com.strayalphaca.domain.diary.model.DiaryWriteData
import com.strayalphaca.domain.diary.repository.DiaryRepository
import com.strayalphaca.domain.model.BaseResponse
import javax.inject.Inject

class UseCaseUploadDiary @Inject constructor(
    private val repository: DiaryRepository
) {
    suspend operator fun invoke(diaryWriteData: DiaryWriteData) : BaseResponse<String> {
        return repository.uploadDiary(diaryWriteData)
    }
}