package com.strayalphaca.data.all.utils

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
