package com.strayalphaca.travel_diary.core.data.room.entity

import androidx.room.Entity

@Entity(primaryKeys = ["recordId", "fileId"])
data class RecordFileEntity(
    val recordId : Int,
    val fileId : Int,
    val positionInRecord : Int
)