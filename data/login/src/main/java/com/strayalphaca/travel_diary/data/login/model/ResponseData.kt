package com.strayalphaca.travel_diary.data.login.model

data class TokensDto(val accessToken : String, val refreshToken : String)

data class SignupResponseBody(val id : String)