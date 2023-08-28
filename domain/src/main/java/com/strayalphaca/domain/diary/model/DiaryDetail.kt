package com.strayalphaca.domain.diary.model

data class DiaryDetail(
    val id : String,
    val date : String,
    val weather : Weather?,
    val feeling: Feeling,
    val content : String,
    val files : List<File>,
    val createdAt : String,
    val voiceFile : File?,
    val cityId : Int = -1,
    val cityName : String ?= null
)

data class DiaryWriteData(
    val recordDate : String,
    val feeling: Feeling,
    val weather: Weather?,
    val content: String,
    val medias : List<String>?,
    val voice : String?,
    val cityId : Int?
)

data class DiaryModifyData(
    val id : String,
    val date : String?,
    val feeling: Feeling?,
    val weather : Weather?,
    val content : String?,
    val medias : List<String>?,
    val voice : String?,
    val cityId : Int?,
    val cityName : String ?= null
)