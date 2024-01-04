package com.susu.data.network

import com.susu.data.model.request.RefreshTokenRequest
import com.susu.data.network.service.TokenService
import com.susu.domain.repository.TokenRepository
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

class TokenAuthenticator @Inject constructor(
    private val tokenRepository: TokenRepository,
    private val tokenService: TokenService,
) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        // 1. refresh token으로 갱신 요청
        val refreshToken = runBlocking { tokenRepository.getRefreshToken().firstOrNull() }
        val accessToken = runBlocking { tokenRepository.getAccessToken().firstOrNull() }

        if (refreshToken == null || accessToken == null) {
            response.close()
            return null
        }

        return runBlocking {
            // 2. access token 갱신
            val tokenResponse = tokenService.refreshAccessToken(
                RefreshTokenRequest(accessToken = accessToken, refreshToken = refreshToken),
            )

            // 2-1. 정상적으로 받지 못하면 request token 까지 만료된 것.
            if (tokenResponse.isSuccessful.not() || tokenResponse.body() == null) {
                // 삭제하여 다시 로그인하도록 유도
                tokenRepository.deleteTokens()
                response.close()
                null
            } else {
                // 3. 헤더에 토큰을 교체한 request 생성
                response.request.newBuilder()
                    .header("X-SUSU-AUTH-TOKEN", tokenResponse.body()!!.accessToken)
                    .build()
            }
        }
    }
}
