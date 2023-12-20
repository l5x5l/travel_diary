package com.strayalphaca.travel_diary.data.login.api

import com.strayalphaca.travel_diary.data.login.model.ChangePasswordRequestBody
import com.strayalphaca.travel_diary.data.login.model.IssueAuthCodeBody
import com.strayalphaca.travel_diary.data.login.model.LoginRequestBody
import com.strayalphaca.travel_diary.data.login.model.SignUpRequestBody
import com.strayalphaca.travel_diary.data.login.model.SignupResponseBody
import com.strayalphaca.travel_diary.data.login.model.TokensDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Query

interface LoginApi {
    @GET("auth/code")
    suspend fun checkAuthCode(@Query("email") email : String, @Query("code") code : String, @Query("type") type : String) : Response<Unit>

    @POST("auth/code")
    suspend fun issueAuthCode(@Body params : IssueAuthCodeBody) : Response<Unit>

    @POST("auth/signup")
    suspend fun signUpByEmail(@Body params : SignUpRequestBody) : Response<SignupResponseBody>

    @POST("auth/login")
    suspend fun login(@Body params : LoginRequestBody) : Response<TokensDto>

    @DELETE("users")
    suspend fun withDraw() : Response<Unit>

    @PATCH("auth/password")
    suspend fun changePassword(@Body params : ChangePasswordRequestBody) : Response<Unit>
}