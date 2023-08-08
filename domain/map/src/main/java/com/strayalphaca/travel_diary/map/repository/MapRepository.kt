package com.strayalphaca.travel_diary.map.repository

import com.strayalphaca.travel_diary.map.model.LocationDiary

interface MapRepository {
    suspend fun getNationWideData(): List<LocationDiary>
    suspend fun getProvinceData(provinceId : Int) : List<LocationDiary>
}