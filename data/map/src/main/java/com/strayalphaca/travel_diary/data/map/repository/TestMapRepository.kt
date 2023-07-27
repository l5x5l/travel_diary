package com.strayalphaca.travel_diary.data.map.repository

import com.strayalphaca.travel_diary.map.model.City
import com.strayalphaca.travel_diary.map.model.CityDiary
import com.strayalphaca.travel_diary.map.model.Province
import com.strayalphaca.travel_diary.map.model.ProvinceDiary
import com.strayalphaca.travel_diary.map.repository.MapRepository
import javax.inject.Inject

// only for testing
class TestMapRepository @Inject constructor() : MapRepository {
    override suspend fun loadProvincePostList(provinceId: Int): List<ProvinceDiary> {
        return listOf(
            ProvinceDiary("", Province.Busan),
            ProvinceDiary("", Province.Sejong),
            ProvinceDiary("", Province.Seoul),
            ProvinceDiary("", Province.Daejeon),
            ProvinceDiary("", Province.Ulsan),
        )
    }

    override suspend fun loadCityPostList(cityIdList: List<Int>): List<CityDiary> {
        return listOf(
            CityDiary(
                thumbnailUri = "", City.findCity(26)
            )
        )
    }

    override suspend fun loadCityPostListByProvince(provinceId: Int): List<CityDiary> {
        return listOf(
            CityDiary(
                thumbnailUri = "", City.findCity(26)
            )
        )
    }
}