package com.strayalphaca.travel_diary.data.file.api

import com.strayalphaca.data.all.model.ListResponseData
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PartMap

interface FileApi {
    @Multipart
    @POST("files")
    suspend fun uploadFiles(@PartMap params : Map<String, RequestBody>) : Response<ListResponseData<String>>
}