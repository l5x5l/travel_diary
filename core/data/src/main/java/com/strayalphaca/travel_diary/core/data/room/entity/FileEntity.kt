package com.strayalphaca.travel_diary.core.data.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FileEntity(
    @PrimaryKey(autoGenerate = true) val id : Int = 0,
    val type : String,
    val filePath : String,
    val createdAt : String
)