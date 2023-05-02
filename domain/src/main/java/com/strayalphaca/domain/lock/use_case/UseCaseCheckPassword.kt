package com.strayalphaca.domain.lock.use_case

import com.strayalphaca.domain.lock.repository.LockRepository
import com.strayalphaca.domain.model.BaseResponse
import javax.inject.Inject

class UseCaseCheckPassword @Inject constructor(
    private val repository: LockRepository
) {
    suspend operator fun invoke(password : String) : BaseResponse<Nothing> {
        return repository.checkPassword(password)
    }
}