package com.strayalphaca.travel_diary.data.diary.model

import com.strayalphaca.travel_diary.diary.model.DiaryModifyData
import com.strayalphaca.travel_diary.diary.model.DiaryWriteData

data class UploadDiaryRequestBody(
    val recordDate : String,
    val feeling : String,
    val weather : String ?= null,
    val content : String,
    val medias : List<String> ?= null,
    val voice : String ?= null,
    val cityId : Int ?= null,
    val place : String ?= null
) {
    companion object {
        fun fromDiaryWriteData(diaryWriteData: DiaryWriteData) : UploadDiaryRequestBody {
            return UploadDiaryRequestBody(
                recordDate = diaryWriteData.recordDate.toString(),
                content = diaryWriteData.content,
                feeling = diaryWriteData.feeling.name,
                weather = diaryWriteData.weather?.name,
                medias = diaryWriteData.medias,
                voice = diaryWriteData.voice,
                cityId = diaryWriteData.cityId
            )
        }
    }
}


data class ModifyDiaryRequestBody(
    val recordDate : String ?= null,
    val feeling : String ?= null,
    val weather : String ?= null,
    val content : String ?= null,
    val medias : List<String> ?= null,
    val voice : String ?= null,
    val cityId : Int ?= null,
    val place : String ?= null
) {
    companion object {
        fun fromDiaryModifyData(diaryModifyData: DiaryModifyData) : ModifyDiaryRequestBody {
            return ModifyDiaryRequestBody(
                recordDate = diaryModifyData.date.toString(),
                content = diaryModifyData.content,
                feeling = diaryModifyData.feeling?.name,
                weather = diaryModifyData.weather?.name,
                medias = diaryModifyData.medias,
                voice = diaryModifyData.voice,
                cityId = diaryModifyData.cityId
            )
        }
    }
}