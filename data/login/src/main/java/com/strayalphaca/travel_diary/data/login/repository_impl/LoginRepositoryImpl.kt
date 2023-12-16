package com.strayalphaca.travel_diary.data.login.repository_impl

import com.strayalphaca.travel_diary.data.login.data_source.LoginDataSource
import com.strayalphaca.travel_diary.core.data.utils.mapBaseResponse
import com.strayalphaca.travel_diary.data.login.utils.tokenDtoToToken
import com.strayalphaca.travel_diary.domain.login.model.Tokens
import com.strayalpaca.travel_diary.core.domain.model.BaseResponse
import com.strayalphaca.travel_diary.domain.login.repository.LoginRepository
import javax.inject.Inject
import javax.inject.Singleton

// repository 는 remote source 에서 가져온 데이터들을 domain layer 에서 사용하는 형식으로 mapping 합니다.
@Singleton
class LoginRepositoryImpl @Inject constructor(
    private val loginDataSource: LoginDataSource
) : LoginRepository {
    override suspend fun emailLogin(email: String, password: String): BaseResponse<Tokens> {
        val response = loginDataSource.postLogin(email, password)
        return mapBaseResponse(response = response, ::tokenDtoToToken)
    }

    override suspend fun emailSignup(email: String, password: String): BaseResponse<String> {
        return loginDataSource.postSignup()
    }

    override suspend fun checkAuthCode(email : String, authCode: String): BaseResponse<Nothing> {
        return loginDataSource.getAuthCode(email, authCode)
    }

    override suspend fun issueAuthCode(email : String): BaseResponse<Nothing> {
        return loginDataSource.postAuthCode(email)
    }

    override suspend fun withdrawal(): BaseResponse<Nothing> {
        return loginDataSource.deleteUser()
    }

    override suspend fun issueRandomPasswordToEmail(email: String): BaseResponse<Nothing> {
        return BaseResponse.EmptySuccess
    }

    override suspend fun changePassword(
        prevPassword: String,
        newPassword: String
    ): BaseResponse<Nothing> {
        return BaseResponse.EmptySuccess
    }

}