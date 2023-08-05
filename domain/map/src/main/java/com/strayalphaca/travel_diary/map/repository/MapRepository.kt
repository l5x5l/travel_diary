package com.strayalphaca.travel_diary.map.repository

import com.strayalphaca.travel_diary.map.model.LocationDiary

interface MapRepository {
    suspend fun loadProvincePostList(provinceId : Int) : List<LocationDiary>
    suspend fun loadCityPostList(cityIdList : List<Int>) : List<LocationDiary>
    suspend fun loadCityPostListByProvince(provinceId : Int) : List<LocationDiary>
}