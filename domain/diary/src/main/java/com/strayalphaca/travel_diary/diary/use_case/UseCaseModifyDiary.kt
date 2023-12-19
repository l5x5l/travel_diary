package com.strayalphaca.travel_diary.diary.use_case

import com.strayalphaca.travel_diary.diary.model.DiaryModifyData
import com.strayalphaca.travel_diary.diary.repository.DiaryRepository
import com.strayalpaca.travel_diary.core.domain.model.BaseResponse
import com.strayalpaca.travel_diary.core.domain.model.ErrorCodeMapper
import com.strayalphaca.travel_diary.diary.di.DiaryErrorCodeMapperProvide
import com.strayalphaca.travel_diary.diary.model.DiaryErrorCodes
import javax.inject.Inject

class UseCaseModifyDiary @Inject constructor(
    private val repository: DiaryRepository,
    @DiaryErrorCodeMapperProvide private val errorCodeMapper : ErrorCodeMapper
) {
    suspend operator fun invoke(diaryModifyData: DiaryModifyData) : BaseResponse<Nothing> {
        if (diaryModifyData.content == "") {
            val errorCode = DiaryErrorCodes.EMPTY_ARGUMENT_DIARY_CONTENT
            return BaseResponse.Failure(errorCode = errorCode, errorMessage = errorCodeMapper.mapCodeToString(errorCode))
        }

        val response = repository.modifyDiary(diaryModifyData)
        return response.mapErrorCodeToMessageIfFailure(errorCodeMapper::mapCodeToString)
    }
}