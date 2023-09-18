package com.strayalphaca.travel_diary.diary.use_case

import com.strayalphaca.travel_diary.diary.model.DiaryWriteData
import com.strayalphaca.travel_diary.diary.repository.DiaryRepository
import com.strayalphaca.domain.model.BaseResponse
import javax.inject.Inject

class UseCaseUploadDiary @Inject constructor(
    private val repository: DiaryRepository
) {
    suspend operator fun invoke(diaryWriteData: DiaryWriteData) : BaseResponse<String> {
        return repository.uploadDiary(diaryWriteData)
    }
}