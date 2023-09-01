package com.strayalphaca.travel_diary.map.repository

import com.strayalphaca.domain.model.BaseResponse
import com.strayalphaca.travel_diary.map.model.LocationDiary

interface MapRepository {
    suspend fun getNationWideData():  BaseResponse<List<LocationDiary>>
    suspend fun getProvinceData(provinceId : Int) : BaseResponse<List<LocationDiary>>
}