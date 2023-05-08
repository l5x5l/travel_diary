package com.strayalphaca.presentation.utils

fun toTimerFormat(timeSecond : Int) : String {
    val minute = timeSecond / 60
    val second = timeSecond % 60

    return String.format("%d:%02d", minute, second)
}

fun minuteIn24HourToHour12(minuteIn24 : Int) : Int {
    val hourIn24 = minuteIn24 / 60
    return if (hourIn24 <= 12) {
        hourIn24
    } else {
        hourIn24 - 12
    }
}
