package com.strayalphaca.travel_diary.diary.model

import com.strayalphaca.domain.all.DiaryDate

data class DiaryDetail(
    val id : String,
    val date : DiaryDate,
    val weather : Weather?,
    val feeling: Feeling,
    val content : String,
    val files : List<File>,
    val createdAt : String,
    val voiceFile : File?,
    val cityId : Int ?= null,
    val cityName : String ?= null
)

data class DiaryWriteData(
    val recordDate : DiaryDate,
    val feeling: Feeling,
    val weather: Weather?,
    val content: String,
    val medias : List<String>?,
    val voice : String?,
    val cityId : Int?
)

data class DiaryModifyData(
    val id : String,
    val date : DiaryDate?,
    val feeling: Feeling?,
    val weather : Weather?,
    val content : String?,
    val medias : List<String>?,
    val voice : String?,
    val cityId : Int?,
    val cityName : String ?= null
)