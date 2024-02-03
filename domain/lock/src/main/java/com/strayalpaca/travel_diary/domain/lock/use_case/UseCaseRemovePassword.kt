package com.strayalpaca.travel_diary.domain.lock.use_case

import com.strayalpaca.travel_diary.core.domain.model.BaseResponse
import com.strayalpaca.travel_diary.domain.lock.repository.LockRepository
import javax.inject.Inject

class UseCaseRemovePassword @Inject constructor(
    private val repository : LockRepository
) {
    suspend operator fun invoke() : BaseResponse<Nothing> {
        return repository.clearPassword()
    }
}