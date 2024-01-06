package com.susu.data.repository

import com.susu.data.model.SnsProviders
import com.susu.data.model.request.AccessTokenRequest
import com.susu.data.model.response.toDomain
import com.susu.data.network.service.UserService
import com.susu.domain.repository.LoginRepository
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val userService: UserService,
) : LoginRepository {
    override suspend fun login(
        oauthAccessToken: String,
    ) = userService.login(
        provider = SnsProviders.Kakao.path,
        accessTokenRequest = AccessTokenRequest(oauthAccessToken),
    ).getOrThrow().toDomain()

    override suspend fun logout() {
        userService.logout().getOrThrow()
    }

    override suspend fun withdraw() {
        userService.withdraw().getOrThrow()
    }
}
