package com.strayalphaca.presentation.models.error_code_mapper.login

import android.content.Context
import com.strayalpaca.travel_diary.core.domain.model.ErrorCodeMapper
import com.strayalphaca.presentation.R
import com.strayalphaca.travel_diary.domain.login.model.LoginErrorCodes.EMPTY_PASSWORD

class SignupErrorCodeMapper constructor(
    private val context : Context
) : ErrorCodeMapper {
    override fun mapCodeToString(errorCode: Int): String {
        return when(errorCode) {
            409 -> {
                context.getString(R.string.login_error_exist_email)
            }
            EMPTY_PASSWORD -> {
                context.getString(R.string.login_error_empty_password)
            }
            else -> {
                context.getString(R.string.unknown_error)
            }
        }
    }

}