package com.strayalphaca.data.login.utils

import com.strayalphaca.data.login.model.TokensDto
import com.strayalphaca.domain.login.model.Tokens
import com.strayalphaca.domain.model.BaseResponse

fun <T, K> mapBaseResponse(response : BaseResponse<T>, mapper : (T) -> K) : BaseResponse<K> {
    return when (response) {
        is BaseResponse.Success -> {
            BaseResponse.Success(mapper(response.data))
        }
        is BaseResponse.Failure -> {
            response
        }
        is BaseResponse.EmptySuccess -> {
            response
        }
    }
}

fun tokenDtoToToken(tokensDto: TokensDto) : Tokens {
    return Tokens(
        accessToken = tokensDto.accessToken,
        refreshToken = tokensDto.refreshToken
    )
}