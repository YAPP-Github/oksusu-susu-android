package com.susu.data.repository

import com.susu.core.model.User
import com.susu.data.remote.model.SnsProviders
import com.susu.data.remote.model.request.toData
import com.susu.data.remote.model.response.toDomain
import com.susu.data.remote.api.SignUpService
import com.susu.domain.repository.SignUpRepository
import javax.inject.Inject

class SignUpRepositoryImpl @Inject constructor(
    private val signUpService: SignUpService,
) : SignUpRepository {

    override suspend fun signUp(
        oauthAccessToken: String,
        user: User,
    ) = signUpService.signUp(
        provider = SnsProviders.Kakao.path,
        accessToken = oauthAccessToken,
        user = user.toData(),
    ).getOrThrow().toDomain()

    override suspend fun canRegister(
        oauthAccessToken: String,
    ): Boolean {
        return signUpService.checkValidRegister(
            provider = SnsProviders.Kakao.path,
            accessToken = oauthAccessToken,
        ).getOrThrow().canRegister
    }
}
