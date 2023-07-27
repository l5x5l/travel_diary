package com.strayalphaca.travel_diary.map.repository

import com.strayalphaca.travel_diary.map.model.CityDiary
import com.strayalphaca.travel_diary.map.model.ProvinceDiary

interface MapRepository {
    suspend fun loadProvincePostList(provinceId : Int) : List<ProvinceDiary>
    suspend fun loadCityPostList(cityIdList : List<Int>) : List<CityDiary>
}