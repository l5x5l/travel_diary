package com.strayalphaca.travel_diary.diary.use_case

import com.strayalphaca.travel_diary.diary.model.DiaryWriteData
import com.strayalphaca.travel_diary.diary.repository.DiaryRepository
import com.strayalphaca.domain.model.BaseResponse
import com.strayalphaca.travel_diary.diary.model.DiaryErrorCodes
import javax.inject.Inject

class UseCaseUploadDiary @Inject constructor(
    private val repository: DiaryRepository
) {
    suspend operator fun invoke(diaryWriteData: DiaryWriteData) : BaseResponse<String> {
        if (diaryWriteData.content.isEmpty()) {
            return BaseResponse.Failure(errorCode = DiaryErrorCodes.EMPTY_ARGUMENT_DIARY_CONTENT, errorMessage = "")
        }

        return repository.uploadDiary(diaryWriteData)
    }
}