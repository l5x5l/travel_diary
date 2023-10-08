package com.strayalphaca.presentation.screens.diary.util

import android.os.Build
import com.strayalphaca.domain.all.DiaryDate

fun DiaryDate.Companion.getInstanceFromDateString(
    dateString : String, dateFormat : String = "yyyy-MM-dd"
) : DiaryDate {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        getInstanceFromDateStringByDateTimeFormatter(dateString, dateFormat)
    } else {
        getInstanceFromDateStringBySimpleDateFormat(dateString, dateFormat)
    }
}
