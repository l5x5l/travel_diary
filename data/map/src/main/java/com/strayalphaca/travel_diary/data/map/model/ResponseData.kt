package com.strayalphaca.travel_diary.data.map.model

data class MapAllLocationResponseBody(
    val id : String,
    val image : ImageDto,
    val provinceId : Int
)

data class MapProvinceResponseBody(
    val id : String,
    val image : ImageDto,
    val groupId : Int
)

data class ImageDto(
    val originName : String,
    val uploadedLink : String,
    val shortLink : String? = null
)