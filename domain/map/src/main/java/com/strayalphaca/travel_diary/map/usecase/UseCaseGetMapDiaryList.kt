package com.strayalphaca.travel_diary.map.usecase

import com.strayalphaca.domain.model.BaseResponse
import com.strayalphaca.travel_diary.map.model.LocationDiary
import com.strayalphaca.travel_diary.map.repository.MapRepository
import javax.inject.Inject

class UseCaseGetMapDiaryList @Inject constructor(
    private val mapRepository: MapRepository
){
    suspend fun getNationWideDataList() : BaseResponse<List<LocationDiary>> {
        return mapRepository.getNationWideData()
    }

    suspend fun getProvinceDataList(provinceId : Int) : BaseResponse<List<LocationDiary>> {
        return mapRepository.getProvinceData(provinceId)
    }
}