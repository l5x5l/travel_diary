package com.strayalphaca.domain.lock.use_case

import com.strayalphaca.domain.lock.repository.LockRepository
import com.strayalphaca.domain.model.BaseResponse
import javax.inject.Inject

class UseCaseSavePassword @Inject constructor(
    private val repository : LockRepository
) {
    suspend operator fun invoke(password : String) : BaseResponse<Nothing> {
        return repository.setPassword(password)
    }
}