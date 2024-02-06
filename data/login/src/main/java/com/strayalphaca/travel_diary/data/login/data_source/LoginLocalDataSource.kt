package com.strayalphaca.travel_diary.data.login.data_source

import com.strayalpaca.travel_diary.core.domain.model.BaseResponse
import com.strayalphaca.travel_diary.core.data.room.database.TrailyRoomDatabase
import com.strayalphaca.travel_diary.data.login.model.TokensDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoginLocalDataSource @Inject constructor(
    private val trailyRoomDatabase: TrailyRoomDatabase
) : LoginDataSource {
    override suspend fun postSignup(): BaseResponse<String> {
        return BaseResponse.Failure(errorCode = -1, errorMessage = "-")
    }

    override suspend fun postAuthCode(email: String): BaseResponse<Nothing> {
        return BaseResponse.Failure(errorCode = -1, errorMessage = "-")
    }

    override suspend fun getAuthCode(email: String, authCode: String): BaseResponse<Nothing> {
        return BaseResponse.Failure(errorCode = -1, errorMessage = "-")
    }

    override suspend fun postLogin(email: String, password: String): BaseResponse<TokensDto> {
        return BaseResponse.Failure(errorCode = -1, errorMessage = "-")
    }

    override suspend fun postRefresh(): BaseResponse<TokensDto> {
        return BaseResponse.Failure(errorCode = -1, errorMessage = "-")
    }

    override suspend fun deleteUser(): BaseResponse<Nothing> {
        withContext(Dispatchers.IO) {
            trailyRoomDatabase.clearAllTables()
        }
        return BaseResponse.EmptySuccess
    }

    override suspend fun getCheckEmail(email: String): BaseResponse<Nothing> {
        return BaseResponse.Failure(errorCode = -1, errorMessage = "-")
    }
}