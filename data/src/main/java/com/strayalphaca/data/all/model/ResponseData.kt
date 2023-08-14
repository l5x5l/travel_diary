package com.strayalphaca.data.all.model

data class FileDto(
    val id : String,
    val type : String = "",
    val shortLink : String = "",
    val originalLink : String = "",
    val createdAt : String = "",
    val updatedAt : String = "",
    val status : String = ""
)


data class DiaryDto(
    val id : String,
    val date : String,
    val feeling : String = "",
    val weather : String? = null,
    val content : String = "",
    val files : List<FileDto> = listOf(),
    val voice : FileDto? = null,
    val createdAt : String = "",
    val updatedAt : String = "",
    val status : String = ""
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