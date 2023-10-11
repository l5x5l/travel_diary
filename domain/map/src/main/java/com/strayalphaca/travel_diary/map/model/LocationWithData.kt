package com.strayalphaca.travel_diary.map.model

data class LocationWithData(
    val location : Location? = null,
    val data : List<LocationDiary> = emptyList()
)