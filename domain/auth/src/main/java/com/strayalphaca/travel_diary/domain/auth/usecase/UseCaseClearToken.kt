package com.strayalphaca.travel_diary.domain.auth.usecase

import com.strayalphaca.travel_diary.domain.auth.repository.AuthRepository
import javax.inject.Inject

class UseCaseClearToken @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke() = authRepository.clearToken()
}