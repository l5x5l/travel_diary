package com.strayalpaca.travel_diary.domain.lock.use_case

import com.strayalpaca.travel_diary.domain.lock.repository.LockRepository
import javax.inject.Inject

class UseCaseUsePassword @Inject constructor(
    private val lockRepository: LockRepository
) {
    suspend operator fun invoke() : Boolean {
        return lockRepository.checkUsingPassword()
    }
}