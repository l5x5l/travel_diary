package com.strayalphaca.travel_diary.data.login.utils

import com.strayalphaca.travel_diary.data.login.model.TokensDto
import com.strayalphaca.travel_diary.domain.login.model.Tokens

fun tokenDtoToToken(tokensDto: TokensDto) : Tokens {
    return Tokens(
        accessToken = tokensDto.accessToken,
        refreshToken = tokensDto.refreshToken
    )
}