package com.strayalphaca.travel_diary.data.map.model

import com.strayalphaca.travel_diary.map.model.City
import com.strayalphaca.travel_diary.map.model.Location
import com.strayalphaca.travel_diary.map.model.LocationDiary
import com.strayalphaca.travel_diary.map.model.LocationId
import com.strayalphaca.travel_diary.map.model.Province

data class MapAllLocationResponseBody(
    val id : String,
    val image : ImageDto,
    val provinceId : Int
) {
    fun toLocationDiary() : LocationDiary =
        LocationDiary(
            thumbnailUri = image.shortLink ?: image.uploadedLink,
            location = Location(
                id = LocationId(provinceId),
                name = Province.findProvince(provinceId).name,
                provinceId = LocationId(provinceId)
            )
        )

}

data class MapProvinceResponseBody(
    val id : String,
    val image : ImageDto,
    val groupId : Int
) {
    fun toLocationDiary(provinceId : Int) : LocationDiary =
        LocationDiary(
            thumbnailUri = image.shortLink ?: image.uploadedLink,
            location = Location(
                id = LocationId(groupId),
                name = City.getSameGroupCityList(groupId).joinToString { "," },
                provinceId = LocationId(provinceId)
            )
        )

}

data class ImageDto(
    val originName : String,
    val uploadedLink : String,
    val shortLink : String? = null
)