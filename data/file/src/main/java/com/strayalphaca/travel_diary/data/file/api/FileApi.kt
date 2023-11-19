package com.strayalphaca.travel_diary.data.file.api

import com.strayalphaca.data.all.model.ListResponseData
import com.strayalphaca.travel_diary.data.file.model.FileDto
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface FileApi {
    @Multipart
    @POST("files")
    suspend fun uploadImage(@Part image : List<MultipartBody.Part>) : Response<ListResponseData<FileDto>>

    @Multipart
    @POST("files")
    suspend fun uploadVideo(@Part video : List<MultipartBody.Part>) : Response<ListResponseData<FileDto>>

    @Multipart
    @POST("files")
    suspend fun uploadVoice(@Part voice : List<MultipartBody.Part>) : Response<ListResponseData<FileDto>>
}