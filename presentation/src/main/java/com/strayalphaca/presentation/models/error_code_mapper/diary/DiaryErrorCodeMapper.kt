package com.strayalphaca.presentation.models.error_code_mapper.diary

import android.content.Context
import com.strayalpaca.travel_diary.core.domain.model.ErrorCodeMapper
import com.strayalphaca.presentation.R
import com.strayalphaca.travel_diary.diary.model.DiaryErrorCodes.EMPTY_ARGUMENT_DIARY_CONTENT

class DiaryErrorCodeMapper constructor(
    private val context : Context
) : ErrorCodeMapper {
    override fun mapCodeToString(errorCode: Int): String {
        return when(errorCode) {
            EMPTY_ARGUMENT_DIARY_CONTENT -> {
                context.getString(R.string.diary_error_empty_diary_content)
            }
            else -> {
                context.getString(R.string.unknown_error)
            }
        }
    }

}