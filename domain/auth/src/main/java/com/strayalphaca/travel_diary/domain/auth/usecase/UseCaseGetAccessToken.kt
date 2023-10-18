package com.strayalphaca.travel_diary.domain.auth.usecase

import com.strayalphaca.travel_diary.domain.auth.repository.AuthRepository
import javax.inject.Inject

class UseCaseGetAccessToken @Inject constructor(
    private val repository : AuthRepository
) {
    suspend operator fun invoke() = repository.getAccessToken()
}