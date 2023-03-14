package com.strayalphaca.domain.login.use_case

import com.strayalphaca.domain.login.model.Tokens
import com.strayalphaca.domain.login.repository.LoginRepository
import com.strayalphaca.domain.model.BaseResponse
import javax.inject.Inject

class UseCaseLogin @Inject constructor(
    private val repository : LoginRepository
) {
    suspend operator fun invoke(email : String, password : String) : BaseResponse<Tokens> {
        return repository.emailLogin(email, password)
    }
}