package com.strayalphaca.data.diary.model

data class UploadDiaryRequestBody(
    val recordDate : String,
    val feeling : String,
    val weather : String ?= null,
    val content : String,
    val medias : List<String> ?= null,
    val voice : String ?= null,
    val cityId : Int ?= null
)

data class ModifyDiaryRequestBody(
    val recordDate : String ?= null,
    val feeling : String ?= null,
    val weather : String ?= null,
    val content : String ?= null,
    val medias : List<String> ?= null,
    val voice : String ?= null,
    val cityId : Int ?= null
)