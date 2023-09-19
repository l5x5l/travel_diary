package com.strayalphaca.travel_diary.domain.alarm.model

data class AlarmInfo(
    val alarmOn : Boolean = false,
    val hour : Int,
    val minute : Int,
    val routeLink : String ?= null
)