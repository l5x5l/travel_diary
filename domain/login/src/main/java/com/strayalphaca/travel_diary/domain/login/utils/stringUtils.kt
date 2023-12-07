package com.strayalphaca.travel_diary.domain.login.utils


internal fun String.removeWhiteSpace() : String {
    return this.replace("\\s".toRegex(), "")
}