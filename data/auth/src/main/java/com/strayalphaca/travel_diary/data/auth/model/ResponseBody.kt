package com.strayalphaca.travel_diary.data.auth.model

data class ReissueTokenResponseBody(
    val accessToken : String,
    val refreshToken : String
)