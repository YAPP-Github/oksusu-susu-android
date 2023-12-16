package com.susu.data.network

import com.susu.domain.repository.TokenRepository
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class TokenInterceptor @Inject constructor(
    private val tokenRepository: TokenRepository,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking {
            tokenRepository.getAccessToken().firstOrNull()
        }
        val request = chain.request().newBuilder().apply {
            addHeader("Authorization", "Bearer $token")
        }
        return chain.proceed(request.build())
    }
}
