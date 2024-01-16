package com.susu.data.data.repository

import com.susu.core.model.SignUpUser
import com.susu.data.remote.api.SignUpService
import com.susu.data.remote.model.request.toData
import com.susu.data.remote.model.response.toDomain
import com.susu.domain.repository.SignUpRepository
import javax.inject.Inject

class SignUpRepositoryImpl @Inject constructor(
    private val signUpService: SignUpService,
) : SignUpRepository {

    override suspend fun signUp(
        provider: String,
        oauthAccessToken: String,
        signUpUser: SignUpUser,
    ) = signUpService.signUp(
        provider = provider,
        accessToken = oauthAccessToken,
        user = signUpUser.toData(),
    ).getOrThrow().toDomain()

    override suspend fun canRegister(
        provider: String,
        oauthAccessToken: String,
    ): Boolean {
        return signUpService.checkValidRegister(
            provider = provider,
            accessToken = oauthAccessToken,
        ).getOrThrow().canRegister
    }
}
