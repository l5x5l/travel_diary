package com.strayalphaca.data.auth.model

data class ReissueTokenResponseBody(
    val accessToken : String,
    val refreshToken : String
)