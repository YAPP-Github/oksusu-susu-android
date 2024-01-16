package com.susu.data.data.repository

import com.susu.data.remote.api.UserService
import com.susu.data.remote.model.SnsProviders
import com.susu.data.remote.model.request.AccessTokenRequest
import com.susu.data.remote.model.response.toDomain
import com.susu.domain.repository.LoginRepository
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val userService: UserService,
) : LoginRepository {
    override suspend fun login(
        provider: String,
        oauthAccessToken: String,
    ) = userService.login(
        provider = provider,
        accessTokenRequest = AccessTokenRequest(oauthAccessToken),
    ).getOrThrow().toDomain()

    override suspend fun logout() {
        userService.logout().getOrThrow()
    }

    override suspend fun withdraw() {
        userService.withdraw().getOrThrow()
    }
}
