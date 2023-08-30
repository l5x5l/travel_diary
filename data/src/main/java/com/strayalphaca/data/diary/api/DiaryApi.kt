package com.strayalphaca.data.diary.api

import com.strayalphaca.data.all.model.DiaryDto
import com.strayalphaca.data.all.model.DiaryItemDto
import com.strayalphaca.data.all.model.ListResponseData
import com.strayalphaca.data.diary.model.ModifyDiaryRequestBody
import com.strayalphaca.data.diary.model.UploadDiaryRequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface DiaryApi {
    @POST("records")
    suspend fun uploadDiary(@Body params : UploadDiaryRequestBody) : Response<String>

    @GET("records/{record}")
    suspend fun loadDetailDiary(@Path("record") record : String) : Response<DiaryDto>

    @DELETE("records/{recordId}")
    suspend fun deleteDiary(@Path("recordId") recordId : String)

    @PATCH("records/{recordId}")
    suspend fun modifyDiary(@Path("recordId") recordId : String, @Body params : ModifyDiaryRequestBody) : Response<Unit>

    @GET("map")
    suspend fun loadDiaryList(@Query("cityId") cityId : Int, @Query("page") page : Int, @Query("offset") offset : Int) : Response<ListResponseData<DiaryItemDto>>

    @GET("map")
    suspend fun loadDiaryListByCityGroup(@Query("cityGroupId") cityGroupId : Int, @Query("page") page : Int, @Query("offset") offset : Int) : Response<ListResponseData<DiaryItemDto>>
}