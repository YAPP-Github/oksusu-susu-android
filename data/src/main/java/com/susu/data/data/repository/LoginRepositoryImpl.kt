package com.susu.data.data.repository

import com.susu.data.remote.api.AuthService
import com.susu.data.remote.model.request.AccessTokenRequest
import com.susu.data.remote.model.response.toDomain
import com.susu.domain.repository.LoginRepository
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val authService: AuthService,
) : LoginRepository {
    override suspend fun login(
        provider: String,
        oauthAccessToken: String,
    ) = authService.login(
        provider = provider,
        accessTokenRequest = AccessTokenRequest(oauthAccessToken),
    ).getOrThrow().toDomain()

    override suspend fun logout() {
        authService.logout().getOrThrow()
    }

    override suspend fun withdraw() {
        authService.withdraw().getOrThrow()
    }
}
