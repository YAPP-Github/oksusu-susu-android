package com.susu.domain.usecase.loginsignup

import com.susu.core.common.runCatchingIgnoreCancelled
import com.susu.core.model.SignUpUser
import com.susu.domain.repository.SignUpRepository
import com.susu.domain.repository.TokenRepository
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val signUpRepository: SignUpRepository,
    private val tokenRepository: TokenRepository,
) {
    suspend operator fun invoke(
        provider: String,
        oauthAccessToken: String,
        signUpUser: SignUpUser,
    ) = runCatchingIgnoreCancelled {
        tokenRepository.saveTokens(
            token = signUpRepository.signUp(provider = provider, oauthAccessToken = oauthAccessToken, signUpUser = signUpUser),
        )
    }
}
