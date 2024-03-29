package com.strayalphaca.travel_diary.data.diary.api

import com.strayalphaca.travel_diary.core.data.model.DiaryDto
import com.strayalphaca.travel_diary.core.data.model.DiaryItemDto
import com.strayalphaca.travel_diary.core.data.model.ListResponseData
import com.strayalphaca.travel_diary.data.diary.model.DiaryTotalResponseBody
import com.strayalphaca.travel_diary.data.diary.model.ModifyDiaryRequestBody
import com.strayalphaca.travel_diary.data.diary.model.UploadDiaryRequestBody
import com.strayalphaca.travel_diary.data.diary.model.UploadResponseBody
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
    suspend fun uploadDiary(@Body params : UploadDiaryRequestBody) : Response<UploadResponseBody>

    @GET("records/{record}")
    suspend fun loadDetailDiary(@Path("record") record : String) : Response<DiaryDto>

    @DELETE("records/{recordId}")
    suspend fun deleteDiary(@Path("recordId") recordId : String) : Response<Unit>

    @PATCH("records/{recordId}")
    suspend fun modifyDiary(@Path("recordId") recordId : String, @Body params : ModifyDiaryRequestBody) : Response<Unit>

    @GET("records/map")
    suspend fun loadDiaryList(@Query("cityId") cityId : Int, @Query("page") page : Int, @Query("offset") offset : Int) : Response<ListResponseData<DiaryItemDto>>

    @GET("records/map")
    suspend fun loadDiaryListByCityGroup(@Query("groupId") cityGroupId : Int, @Query("page") page : Int, @Query("offset") offset : Int) : Response<ListResponseData<DiaryItemDto>>

    @GET("records/total")
    suspend fun diaryTotalCount() : Response<DiaryTotalResponseBody>
}