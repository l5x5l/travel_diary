package com.strayalphaca.travel_diary.data.map.model

import com.strayalphaca.travel_diary.map.model.City
import com.strayalphaca.travel_diary.map.model.Location
import com.strayalphaca.travel_diary.map.model.LocationDiary
import com.strayalphaca.travel_diary.map.model.LocationId
import com.strayalphaca.travel_diary.map.model.LocationType
import com.strayalphaca.travel_diary.map.model.Province

data class MapAllLocationResponseBody(
    val id : String,
    val image : ImageFileInfoDto?,
    val provinceId : Int
) {
    fun toLocationDiary() : LocationDiary =
        LocationDiary(
            id = id,
            thumbnailUri = image?.thumbnailUri(),
            location = Location(
                id = LocationId(provinceId),
                name = Province.findProvince(provinceId).name,
                provinceId = LocationId(provinceId)
            )
        )

}

data class MapProvinceResponseBody(
    val id : String,
    val image : ImageFileInfoDto?,
    val groupId : Int
) {
    fun toLocationDiary(provinceId : Int) : LocationDiary =
        LocationDiary(
            id = id,
            thumbnailUri = image?.thumbnailUri(),
            location = Location(
                id = LocationId(groupId),
                name = City.getGroupName(groupId),
                provinceId = LocationId(provinceId),
                type = LocationType.CITY_GROUP
            )
        )

}

data class ImageFileInfoDto(
    val originName : String,
    val uploadedLink : String,
    val shortLink : String? = null
) {
    fun thumbnailUri() : String {
        return shortLink ?: uploadedLink
    }
}