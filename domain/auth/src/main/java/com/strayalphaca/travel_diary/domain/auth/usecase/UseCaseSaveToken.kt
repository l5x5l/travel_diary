package com.strayalphaca.travel_diary.domain.auth.usecase

import com.strayalphaca.travel_diary.domain.auth.repository.AuthRepository
import javax.inject.Inject

class UseCaseSaveToken @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(
        accessToken : String? = null, refreshToken : String? = null
    ) {
        accessToken?.let {
            authRepository.setAccessToken(it)
        }
        refreshToken?.let {
            authRepository.setRefreshToken(it)
        }
    }
}