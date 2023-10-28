package com.strayalphaca.travel_diary.diary.use_case

import com.strayalphaca.travel_diary.diary.model.DiaryModifyData
import com.strayalphaca.travel_diary.diary.repository.DiaryRepository
import com.strayalphaca.domain.model.BaseResponse
import com.strayalphaca.travel_diary.diary.model.DiaryErrorCodes
import javax.inject.Inject

class UseCaseModifyDiary @Inject constructor(
    private val repository: DiaryRepository
) {
    suspend operator fun invoke(diaryModifyData: DiaryModifyData) : BaseResponse<Nothing> {
        if (diaryModifyData.content == "") {
            return BaseResponse.Failure(errorCode = DiaryErrorCodes.EMPTY_ARGUMENT_DIARY_CONTENT, errorMessage = "")
        }

        return repository.modifyDiary(diaryModifyData)
    }
}