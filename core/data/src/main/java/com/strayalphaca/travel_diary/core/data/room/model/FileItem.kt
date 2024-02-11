package com.strayalphaca.travel_diary.core.data.room.model

data class FileItem(
    val id : Int,
    val filePath : String,
    val type : String,
    val positionInRecord : Int
)