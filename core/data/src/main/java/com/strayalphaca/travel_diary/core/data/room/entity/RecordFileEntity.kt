package com.strayalphaca.travel_diary.core.data.room.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = RecordEntity::class,
            parentColumns = ["id"],
            childColumns = ["recordId"],
            onDelete = ForeignKey.SET_NULL
        ),
        ForeignKey(
            entity = FileEntity::class,
            parentColumns = ["id"],
            childColumns = ["fileId"],
            onDelete = ForeignKey.CASCADE
        ),
    ]
)
data class RecordFileEntity(
    val recordId : Int?,
    val fileId : Int,
    val positionInRecord : Int
) {
    @PrimaryKey(autoGenerate = true) var id : Int = 0
}