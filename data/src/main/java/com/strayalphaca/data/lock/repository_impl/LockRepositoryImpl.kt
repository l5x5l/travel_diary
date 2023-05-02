package com.strayalphaca.data.lock.repository_impl

import com.strayalphaca.data.lock.data_source.LockDataSource
import com.strayalphaca.domain.lock.repository.LockRepository
import com.strayalphaca.domain.model.BaseResponse
import javax.inject.Inject

class LockRepositoryImpl @Inject constructor(
    private val dataSource: LockDataSource
): LockRepository {
    override suspend fun checkPassword(password: String): BaseResponse<Nothing> {
        val correctPassword = dataSource.getPassword()

        return if (correctPassword == password) {
            BaseResponse.EmptySuccess
        } else {
            BaseResponse.Failure(errorCode = 403, errorMessage = "not matched")
        }
    }

    override suspend fun setPassword(password: String): BaseResponse<Nothing> {
        dataSource.setPassword(password)
        return BaseResponse.EmptySuccess
    }

    override suspend fun checkUsingPassword(): Boolean {
        val password = dataSource.getPassword()
        return password != null
    }
}