package com.susu.data.repository

import com.susu.core.model.User
import com.susu.data.model.SnsProviders
import com.susu.data.model.request.AccessTokenRequest
import com.susu.data.model.request.RefreshTokenRequest
import com.susu.data.model.toData
import com.susu.data.model.toDomain
import com.susu.data.network.AuthService
import com.susu.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authService: AuthService,
) : AuthRepository {
    override suspend fun login(
        accessToken: String,
    ) = kotlin.runCatching {
        authService.login(
            provider = SnsProviders.Kakao.name,
            accessTokenRequest = AccessTokenRequest(accessToken),
        ).toDomain()
    }

    override suspend fun signUp(
        snsAccessToken: String,
        user: User,
    ) = runCatching {
        authService.signUp(
            provider = SnsProviders.Kakao.name,
            accessToken = snsAccessToken,
            user = user.toData(),
        ).toDomain()
    }

    override suspend fun canRegister(
        accessToken: String,
    ): Boolean {
        return authService.checkValidRegister(
            provider = SnsProviders.Kakao.name,
            accessTokenRequest = AccessTokenRequest(accessToken),
        ).canRegister
    }

    override suspend fun logout() {
        authService.logout()
    }

    override suspend fun refreshAccessToken(refreshToken: String) = runCatching {
        authService.refreshAccessToken(
            refreshTokenRequest = RefreshTokenRequest(refreshToken),
        ).toDomain()
    }
}