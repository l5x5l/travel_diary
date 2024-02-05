package com.strayalphaca.travel_diary.core.data.room.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.ForeignKey.Companion.SET_NULL
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = LocationEntity::class,
            parentColumns = ["id"],
            childColumns = ["locationId"],
            onDelete = SET_NULL,
            onUpdate = CASCADE
        )
    ]
)
data class RecordEntity(
    @PrimaryKey(autoGenerate = true) val id : Int = 0,
    val content : String,
    val weather : String,
    val feeling : String,
    val locationId : Int?,
    val createdAt : String,
    val updatedAt : String
)