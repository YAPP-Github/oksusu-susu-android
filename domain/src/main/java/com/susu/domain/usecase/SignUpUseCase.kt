package com.susu.domain.usecase

import com.susu.core.common.runCatchingIgnoreCancelled
import com.susu.core.model.User
import com.susu.domain.repository.SignUpRepository
import com.susu.domain.repository.TokenRepository
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val signUpRepository: SignUpRepository,
    private val tokenRepository: TokenRepository,
) {
    suspend operator fun invoke(
        oauthAccessToken: String,
        user: User,
    ) = runCatchingIgnoreCancelled {
        tokenRepository.saveTokens(
            token = signUpRepository.signUp(oauthAccessToken = oauthAccessToken, user = user),
        )
    }
}
