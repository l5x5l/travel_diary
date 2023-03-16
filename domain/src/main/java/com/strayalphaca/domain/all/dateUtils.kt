package com.strayalphaca.domain.all

/**
 * 각 월마다 일의 총 개수를 리턴합니다.
 */
fun getDayAmountOfMonth(year : Int, month : Int) : Int {
    return when (month) {
        2 -> {
            if (year % 4 != 0 || (year % 100 == 0 && year % 400 != 0)) { 28 }
            else { 29 }
        }
        in listOf(1,3,5,7,8,10,12) -> { 31 }
        else -> { 30 }
    }
}