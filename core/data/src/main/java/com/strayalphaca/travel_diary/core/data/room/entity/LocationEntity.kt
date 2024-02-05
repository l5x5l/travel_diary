package com.strayalphaca.travel_diary.core.data.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LocationEntity(
    @PrimaryKey val id : Int = 0,
    val provinceId : Int,
    val cityGroupId : Int,
    val cityName : String
)