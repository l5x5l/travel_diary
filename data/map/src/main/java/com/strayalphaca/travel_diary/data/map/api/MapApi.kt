package com.strayalphaca.travel_diary.data.map.api

import com.strayalphaca.travel_diary.core.data.model.ListResponseData
import com.strayalphaca.travel_diary.data.map.model.MapAllLocationResponseBody
import com.strayalphaca.travel_diary.data.map.model.MapProvinceResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MapApi {
    @GET("records/map")
    suspend fun getAllLocationDiary() : Response<ListResponseData<MapAllLocationResponseBody>>

    @GET("records/map")
    suspend fun getProvinceDiary(@Query("provinceId") provinceId : Int) : Response<ListResponseData<MapProvinceResponseBody>>
}