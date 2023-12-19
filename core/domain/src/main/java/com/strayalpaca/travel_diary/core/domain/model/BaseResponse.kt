package com.strayalpaca.travel_diary.core.domain.model

sealed class BaseResponse<out T> {
    data class Success<T>(
        val data : T,
    ) : BaseResponse<T>()

    data class Failure(
        val errorCode : Int,
        val errorMessage : String
    ) : BaseResponse<Nothing>()

    object EmptySuccess : BaseResponse<Nothing>()

    fun mapErrorCodeToMessageIfFailure(errorCodeMapper : (Int) -> String) : BaseResponse<T> {
        return if (this is Failure) {
            this.copy(errorMessage = errorCodeMapper(errorCode))
        } else {
            this
        }
    }
}
