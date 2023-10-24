package com.strayalphaca.travel_diary.domain.auth.usecase

import com.strayalphaca.travel_diary.domain.auth.repository.AuthRepository
import javax.inject.Inject

class UseCaseSaveToken @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(
        accessToken : String?, refreshToken : String?
    ) {
        accessToken?.let {
            authRepository.setAccessToken(it)
        }
        refreshToken?.let {
            authRepository.setRefreshToken(it)
        }
    }
}