package com.strayalphaca.data.all.model

data class MediaFileInfoDto(
    val originName : String,
    val type : String = "",
    val uploadedLink : String = "",
    val shortLink : String? = null,
    val thumbnailLink : String ?= null,
    val thumbnailShortLink : String ?= null
)


data class DiaryDto(
    val id : String,
    val date : String,
    val feeling : String = "",
    val weather : String? = null,
    val content : String = "",
    val medias : List<MediaFileInfoDto> = listOf(),
    val voice : VoiceFileInDiaryDto? = null,
    val createdAt : String = "",
    val cityId : Int ?= null,
    val place : String ?= null
) {
    var dateStringFormat : String ?= null
}

data class VoiceFileInDiaryDto(
    val originName : String,
    val uploadedLink : String = "",
    val shortLink : String? = null
)

data class DiaryItemDto(
    val id : String,
    val image : ImageDto?,
    val city : CityDto,
    val provinceId : Int
)

data class CityDto(
    val id : Int,
    val name : String
)

data class ImageDto(
    val originalName : String,
    val uploadedLink : String,
    val shortLink : String?
)