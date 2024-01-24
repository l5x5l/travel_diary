package com.strayalphaca.travel_diary.network

import okhttp3.Interceptor
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import java.net.SocketTimeoutException
import javax.inject.Inject

class BaseInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return try {
            chain.proceed(chain.request())
        } catch (e : SocketTimeoutException) {
            return Response.Builder()
                .request(chain.request())
                .protocol(Protocol.HTTP_1_1)
                .code(408)
                .message("socket timeout error")
                .body("{${e}}".toResponseBody(null)).build()
        }
    }
}