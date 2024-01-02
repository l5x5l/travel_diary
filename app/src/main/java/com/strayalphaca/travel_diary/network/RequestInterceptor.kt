package com.strayalphaca.travel_diary.network

import com.strayalphaca.travel_diary.domain.auth.repository.AuthRepository
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import java.net.SocketTimeoutException
import javax.inject.Inject

class RequestInterceptor @Inject constructor(
    private val needRefreshTokenUrl : Set<String>,
    private val authRepository: AuthRepository
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = setRequestHeader(chain)
        val response = try {
            chain.proceed(request)
        } catch (e : SocketTimeoutException) {
            return Response.Builder()
                .request(request)
                .protocol(Protocol.HTTP_1_1)
                .code(408)
                .message("socket timeout error")
                .body("{${e}}".toResponseBody(null)).build()
        }

        val currentRefreshToken = authRepository.getRefreshToken()
        if (response.code != 407 && response.code != 401) {
            return response
        }

        synchronized(this) {
            // request 를 보냈을 당시 refreshToken 과 현재 refreshToken 이 다른 경우는
            // 이미 다른 토큰 재발급 요청을 인해 토큰 발급이 이루어진 경우이므로, 토큰 재발급을 수행하지 않습니다.
            if (currentRefreshToken != authRepository.getRefreshToken()) {
                return@synchronized
            }

            runBlocking {
                authRepository.reissueAccessToken()
            }
        }

        val requestAfterRefreshToken = setRequestHeader(chain)
        response.close()
        return chain.proceed(requestAfterRefreshToken)
    }

    private fun setRequestHeader(chain: Interceptor.Chain) : Request {
        val builder = chain.request().newBuilder()

        val accessToken = authRepository.getAccessToken()
        val refreshToken = authRepository.getRefreshToken()

        accessToken?.let { token ->
            builder.addHeader("Authorization", "Bearer $token")
        }

        if (
            chain.request().url.toString() in needRefreshTokenUrl &&
            refreshToken != null
        ) {
            builder.addHeader("RefreshToken", refreshToken)
        }

        return builder.build()
    }

}