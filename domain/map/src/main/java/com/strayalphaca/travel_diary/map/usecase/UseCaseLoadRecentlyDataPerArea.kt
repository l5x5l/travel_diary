package com.strayalphaca.travel_diary.map.usecase

import com.strayalphaca.travel_diary.map.model.CityDiary
import com.strayalphaca.travel_diary.map.model.ProvinceDiary
import com.strayalphaca.travel_diary.map.repository.MapRepository
import javax.inject.Inject

class UseCaseLoadRecentlyDataPerArea @Inject constructor(
    private val repository: MapRepository
){
    suspend operator fun invoke(provinceId : Int) : List<ProvinceDiary> {
        return repository.loadProvincePostList(provinceId = provinceId)
    }

    suspend fun loadDataListOfCity(provinceId : Int) : List<CityDiary> {
        return repository.loadCityPostListByProvince(provinceId)
    }
}