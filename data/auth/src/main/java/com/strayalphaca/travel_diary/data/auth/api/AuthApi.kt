package com.strayalphaca.travel_diary.data.auth.api

import com.strayalphaca.travel_diary.data.auth.model.ReissueTokenResponseBody
import retrofit2.Response
import retrofit2.http.HeaderMap
import retrofit2.http.POST

interface AuthApi {
    @POST("auths/refresh")
    suspend fun reissueToken(@HeaderMap headers : Map<String, String>) : Response<ReissueTokenResponseBody>
}