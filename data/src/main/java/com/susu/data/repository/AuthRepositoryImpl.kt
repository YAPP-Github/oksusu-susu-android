package com.susu.data.repository

import com.susu.core.model.User
import com.susu.data.model.SnsProviders
import com.susu.data.model.request.AccessTokenRequest
import com.susu.data.model.toData
import com.susu.data.model.toDomain
import com.susu.data.network.service.SignUpService
import com.susu.data.network.service.UserService
import com.susu.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val userService: UserService,
    private val signUpService: SignUpService,
) : AuthRepository {
    override suspend fun login(
        accessToken: String,
    ) = kotlin.runCatching {
        userService.login(
            provider = SnsProviders.Kakao.path,
            accessTokenRequest = AccessTokenRequest(accessToken),
        ).toDomain()
    }

    override suspend fun signUp(
        snsAccessToken: String,
        user: User,
    ) = runCatching {
        signUpService.signUp(
            provider = SnsProviders.Kakao.path,
            accessToken = snsAccessToken,
            user = user.toData(),
        ).toDomain()
    }

    override suspend fun canRegister(
        accessToken: String,
    ): Boolean {
        return signUpService.checkValidRegister(
            provider = SnsProviders.Kakao.path,
            accessToken = accessToken,
        ).canRegister
    }

    override suspend fun logout() {
        userService.logout()
    }

    override suspend fun withdraw() {
        userService.withdraw()
    }
}
