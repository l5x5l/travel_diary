package com.strayalphaca.travel_diary.map.model

data class Location(
    val id : LocationId,
    val name : String,
    val provinceId : LocationId,
    val type : LocationType = LocationType.PROVINCE
) {
    companion object {
        fun getInstanceByProvinceId(provinceId : Int) : Location {
            return Location(
                id = LocationId(provinceId),
                name = Province.findProvince(provinceId).name,
                provinceId = LocationId(provinceId),
                type = LocationType.PROVINCE
            )
        }
    }
}

enum class LocationType {
    PROVINCE, CITY_GROUP
}