package com.strayalphaca.travel_diary.map.model

data class Location(
    val id : LocationId,
    val name : String,
    val provinceId : LocationId,
    val type : LocationType = LocationType.PROVINCE
)

enum class LocationType {
    PROVINCE, CITY_GROUP, CITY
}