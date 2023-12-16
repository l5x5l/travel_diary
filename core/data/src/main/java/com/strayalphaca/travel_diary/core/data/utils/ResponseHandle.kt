package com.strayalphaca.travel_diary.core.data.utils

import com.strayalpaca.travel_diary.core.domain.model.BaseResponse
import retrofit2.Response

fun<T, G> responseToBaseResponseWithMapping(
    response : Response<T>,
    mappingFunction : (T) -> G
) : BaseResponse<G> {
    val responseBody = response.body()
    return if (response.isSuccessful && responseBody != null) {
        BaseResponse.Success(data = mappingFunction(responseBody))
    } else {
        BaseResponse.Failure(
            errorCode = response.code(),
            errorMessage = response.message()
        )
    }
}

fun<T> responseToBaseResponse(
    response : Response<T>
) : BaseResponse<T> {
    val responseBody = response.body()
    return if (response.isSuccessful && responseBody != null) {
        BaseResponse.Success(data = responseBody)
    } else {
        BaseResponse.Failure(
            errorCode = response.code(),
            errorMessage = response.message()
        )
    }
}

fun voidResponseToBaseResponse(response : Response<*>) : BaseResponse<Nothing> {
    return if (response.isSuccessful || response.code() == 200) {
        BaseResponse.EmptySuccess
    } else {
        BaseResponse.Failure(
            errorCode = response.code(),
            errorMessage = response.message()
        )
    }
}