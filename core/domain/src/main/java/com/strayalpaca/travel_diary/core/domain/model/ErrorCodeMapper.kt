package com.strayalpaca.travel_diary.core.domain.model

interface ErrorCodeMapper {
    fun mapCodeToString(errorCode : Int) : String
}