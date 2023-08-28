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
    val voice : MediaFileInfoDto? = null,
    val createdAt : String = "",
    val cityId : Int = 1
)

data class DiaryItemDto(
    val id : String,
    val image : ImageDto?,
    val cityId : Int
)

data class ImageDto(
    val originalName : String,
    val uploadedLink : String,
    val shortLink : String?
)