package com.strayalphaca.presentation.models.error_code_mapper

import android.content.Context
import com.strayalpaca.travel_diary.core.domain.model.ErrorCodeMapper
import com.strayalphaca.presentation.R

class DefaultErrorCodeMapper constructor(
    private val specificDomainErrorCodeMapper: ErrorCodeMapper?,
    private val context : Context
): ErrorCodeMapper {
    private val defaultErrorCodeSet = setOf(500, 404)

    override fun mapCodeToString(errorCode: Int): String {
        return if (errorCode in defaultErrorCodeSet) {
            mapDefaultCodeToString(errorCode = errorCode)
        } else {
            specificDomainErrorCodeMapper?.mapCodeToString(errorCode = errorCode)
                ?: context.getString(R.string.unknown_error)
        }
    }

    private fun mapDefaultCodeToString(errorCode: Int) : String {
        return when(errorCode) {
            404 -> context.getString(R.string.unknown_error)
            500 -> context.getString(R.string.server_side_error)
            else -> context.getString(R.string.unknown_error)
        }
    }
}