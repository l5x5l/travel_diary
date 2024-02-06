package com.strayalphaca.travel_diary.core.data.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FileEntity(
    val type : String,
    val filePath : String,
    val createdAt : String
) {
    @PrimaryKey(autoGenerate = true) var id : Int = 0
}