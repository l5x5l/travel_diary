package com.strayalphaca.data.login.utils

import com.strayalphaca.data.login.model.TokensDto
import com.strayalphaca.domain.login.model.Tokens

fun tokenDtoToToken(tokensDto: TokensDto) : Tokens {
    return Tokens(
        accessToken = tokensDto.accessToken,
        refreshToken = tokensDto.refreshToken
    )
}