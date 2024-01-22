package com.susu.domain.usecase.loginsignup

import com.susu.core.common.runCatchingIgnoreCancelled
import com.susu.domain.repository.LoginRepository
import com.susu.domain.repository.TokenRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val loginRepository: LoginRepository,
    private val tokenRepository: TokenRepository,
) {
    suspend operator fun invoke(provider: String, oauthAccessToken: String) = runCatchingIgnoreCancelled {
        tokenRepository.saveTokens(
            token = loginRepository.login(provider = provider, oauthAccessToken = oauthAccessToken),
        )
    }
}
