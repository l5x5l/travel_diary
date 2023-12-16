package com.strayalpaca.travel_diary.domain.lock.use_case

import com.strayalpaca.travel_diary.domain.lock.repository.LockRepository
import com.strayalpaca.travel_diary.core.domain.model.BaseResponse
import javax.inject.Inject

class UseCaseCheckPassword @Inject constructor(
    private val repository: LockRepository
) {
    suspend operator fun invoke(password : String) : BaseResponse<Nothing> {
        return repository.checkPassword(password)
    }
}