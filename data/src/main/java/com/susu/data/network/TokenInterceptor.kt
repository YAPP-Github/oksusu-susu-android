package com.susu.data.network

import com.susu.core.model.exception.RefreshTokenExpiredException
import com.susu.domain.repository.TokenRepository
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject

class TokenInterceptor @Inject constructor(
    private val tokenRepository: TokenRepository,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking {
            tokenRepository.getAccessToken().firstOrNull()
        }

        return try {
            val request = chain.request().newBuilder().apply {
                addHeader("X-SUSU-AUTH-TOKEN", token ?: "")
            }
            chain.proceed(request.build())
        } catch (e: Exception) {
            throw RefreshTokenExpiredException()
        }
    }
}
