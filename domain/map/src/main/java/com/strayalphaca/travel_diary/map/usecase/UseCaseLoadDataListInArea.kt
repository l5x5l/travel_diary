package com.strayalphaca.travel_diary.map.usecase

import com.strayalphaca.travel_diary.map.model.CityDiary
import com.strayalphaca.travel_diary.map.repository.MapRepository
import javax.inject.Inject

class UseCaseLoadDataListInArea @Inject constructor(
    private val repository: MapRepository
) {
    suspend operator fun invoke(cityIdList : List<Int>) : List<CityDiary> {
        return repository.loadCityPostList(cityIdList = cityIdList)
    }
}