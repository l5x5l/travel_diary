package com.strayalphaca.presentation.models.error_code_mapper.login

import android.content.Context
import com.strayalpaca.travel_diary.core.domain.model.ErrorCodeMapper
import com.strayalphaca.presentation.R
import com.strayalphaca.travel_diary.domain.login.model.LoginErrorCodes.INVALID_AUTH_CODE_FORMAT
import com.strayalphaca.travel_diary.domain.login.model.LoginErrorCodes.INVALID_EMAIL_FORMAT

class AuthCodeErrorCodeMapper constructor(
    private val context : Context
) : ErrorCodeMapper {
    override fun mapCodeToString(errorCode: Int): String {
        return when (errorCode) {
            INVALID_EMAIL_FORMAT -> {
                context.getString(R.string.login_error_invalid_email_format)
            }
            INVALID_AUTH_CODE_FORMAT -> {
                context.getString(R.string.login_error_invalid_auth_code_format)
            }
            400 -> {
                context.getString(R.string.login_error_invalid_auth_code)
            }
            409 -> {
                context.getString(R.string.login_error_exist_email)
            }
            else -> {
                context.getString(R.string.unknown_error)
            }
        }
    }
}