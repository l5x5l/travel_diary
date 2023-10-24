package com.strayalphaca.travel_diary.domain.login.model

object LoginErrorCodes {
    const val INPUT_EMAIL_AND_PASSWORD = 1
    const val INVALID_EMAIL_FORMAT = 2
    const val EXIST_EMAIL = 3
    const val INVALID_EMAIL_OR_PASSWORD = 4
    const val DAILY_LIMIT_EXCEEDED_ISSUE_AUTH_CODE = 5
    const val INVALID_AUTH_CODE = 5
}