package com.strayalphaca.travel_diary.domain.login.utils

fun isEmailFormat(email: String): Boolean {
    val emailPattern = Regex("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")
    return emailPattern.matches(email)
}