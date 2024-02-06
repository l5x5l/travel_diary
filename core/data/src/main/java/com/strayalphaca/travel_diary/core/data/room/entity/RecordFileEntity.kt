package com.strayalphaca.travel_diary.core.data.room.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = FileEntity::class,
            parentColumns = ["id"],
            childColumns = ["fileId"],
            onDelete = CASCADE
        ),
        ForeignKey(
            entity = RecordEntity::class,
            parentColumns = ["id"],
            childColumns = ["recordId"],
            onDelete = CASCADE
        )
    ]
)
data class RecordFileEntity(
    @PrimaryKey(autoGenerate = true) val id : Int = 0,
    val recordId : Int,
    val fileId : Int
)