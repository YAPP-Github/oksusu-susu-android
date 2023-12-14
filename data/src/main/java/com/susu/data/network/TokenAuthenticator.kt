package com.susu.data.network

import com.susu.domain.repository.TokenRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

class TokenAuthenticator @Inject constructor(
    private val tokenRepository: TokenRepository,
) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        // 1. refresh token으로 갱신 요청
        val refreshToken = runBlocking { tokenRepository.getRefreshToken().first() }

        if (refreshToken == null) {
            response.close()
            return null
        }

        return runBlocking {
            // 2. access token 갱신
            val refreshedAccessToken = tokenRepository.refreshAccessToken()

            // 2-1. 정상적으로 받지 못하면 request token 까지 만료된 것.
            if (refreshedAccessToken == null) {
                // 삭제하여 다시 로그인하도록 유도
                tokenRepository.deleteRefreshToken()
                tokenRepository.deleteAccessToken()
                response.close()
                null
            } else {
                // 3. 헤더에 토큰을 교체한 request 생성
                response.request.newBuilder()
                    .header("Authorization", "Bearer $refreshedAccessToken")
                    .build()
            }
        }
    }
}