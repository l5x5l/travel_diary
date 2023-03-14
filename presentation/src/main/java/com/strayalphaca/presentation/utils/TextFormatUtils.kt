package com.strayalphaca.presentation.utils

fun toTimerFormat(timeSecond : Int) : String {
    val minute = timeSecond / 60
    val second = timeSecond % 60

    return String.format("%d:%02d", minute, second)
}