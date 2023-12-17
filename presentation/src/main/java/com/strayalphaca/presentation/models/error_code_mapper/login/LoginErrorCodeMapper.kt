package com.strayalphaca.presentation.models.error_code_mapper.login

import android.content.Context
import com.strayalpaca.travel_diary.core.domain.model.ErrorCodeMapper
import com.strayalphaca.presentation.R
import com.strayalphaca.travel_diary.domain.login.model.LoginErrorCodes.EMPTY_EMAIL_OR_PASSWORD
import com.strayalphaca.travel_diary.domain.login.model.LoginErrorCodes.INVALID_EMAIL_FORMAT

class LoginErrorCodeMapper constructor(
    private val context : Context
) : ErrorCodeMapper {
    override fun mapCodeToString(errorCode: Int): String {
        return when(errorCode) {
            EMPTY_EMAIL_OR_PASSWORD -> {
                context.getString(R.string.login_error_empty_email_or_password)
            }
            INVALID_EMAIL_FORMAT -> {
                context.getString(R.string.login_error_invalid_email_format)
            }
            409 -> {
                context.getString(R.string.login_error_non_exist_email_or_password)
            }
            else -> {
                context.getString(R.string.unknown_error)
            }
        }
    }
}