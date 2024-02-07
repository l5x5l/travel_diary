package com.strayalphaca.travel_diary.diary.model

import com.strayalpaca.travel_diary.core.domain.model.DiaryDate

data class DiaryDetail(
    val id : String,
    val date : DiaryDate,
    val weather : Weather,
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
    val weather: Weather,
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
    val mediasFiles : List<File>?,
    val voice : String?,
    val cityId : Int?,
    val cityName : String ?= null
) {
    fun toDiaryItem() : DiaryItem {
        return DiaryItem(id = id, imageUrl = mediasFiles?.getOrNull(0)?.fileLink, cityName = cityName ?: "-")
    }

    val medias = mediasFiles?.map { it.id }
}