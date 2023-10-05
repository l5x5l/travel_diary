package com.strayalphaca.travel_diary.diary.model

import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale

class DiaryDate(
    val year : Int,
    val month : Int,
    val day : Int
) {
    override fun toString(): String {
        val decimalFormat = DecimalFormat("00")
        return "$year-${decimalFormat.format(month)}-${decimalFormat.format(day)}"
    }

    companion object {
        fun getInstanceFromCalendar(calendar: Calendar = Calendar.getInstance()) : DiaryDate {
            return DiaryDate(
                year = calendar.get(Calendar.YEAR),
                month = calendar.get(Calendar.MONTH) + 1,
                day = calendar.get(Calendar.DAY_OF_MONTH)
            )
        }

        @Suppress("NewApi")
        fun getInstanceFromDateStringByDateTimeFormatter(
            dateString : String,
            dateFormat : String = "yyyy-MM-dd"
        ) : DiaryDate {
            val formatter = DateTimeFormatter.ofPattern(dateFormat, Locale.KOREA)
            val date = LocalDate.parse(dateString, formatter)

            return DiaryDate(
                year = date.year,
                month = date.monthValue,
                day = date.dayOfMonth
            )
        }

        fun getInstanceFromDateStringBySimpleDateFormat(
            dateString : String,
            dateFormat : String = "yyyy-MM-dd"
        ) : DiaryDate {
            val formatter = SimpleDateFormat(dateFormat, Locale.KOREA)
            val date = formatter.parse(dateString)

            requireNotNull(date)

            val calendar = Calendar.getInstance()
            calendar.time = date

            return DiaryDate(
                year = calendar.get(Calendar.YEAR),
                month = calendar.get(Calendar.MONTH) + 1,
                day = calendar.get(Calendar.DAY_OF_MONTH)
            )
        }
    }
}