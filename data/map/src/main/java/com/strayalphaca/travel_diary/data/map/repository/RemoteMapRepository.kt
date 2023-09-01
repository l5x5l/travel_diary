package com.strayalphaca.travel_diary.data.map.repository

import com.strayalphaca.data.all.utils.responseToBaseResponseWithMapping
import com.strayalphaca.domain.model.BaseResponse
import com.strayalphaca.travel_diary.data.map.api.MapApi
import com.strayalphaca.travel_diary.map.model.LocationDiary
import com.strayalphaca.travel_diary.map.repository.MapRepository
import retrofit2.Retrofit
import javax.inject.Inject

class RemoteMapRepository @Inject constructor(
    retrofit : Retrofit
) : MapRepository {
    private val mapRetrofit = retrofit.create(MapApi::class.java)

    override suspend fun getNationWideData(): BaseResponse<List<LocationDiary>> {
        val response = mapRetrofit.getAllLocationDiary()
        return responseToBaseResponseWithMapping(
            response = response,
            mappingFunction = { it.data.map { responseData -> responseData.toLocationDiary() }}
        )
    }

    override suspend fun getProvinceData(provinceId: Int): BaseResponse<List<LocationDiary>> {
        val response = mapRetrofit.getProvinceDiary(provinceId)
        return responseToBaseResponseWithMapping(
            response = response,
            mappingFunction = { it.data.map { responseData -> responseData.toLocationDiary(provinceId) }}
        )
    }

}