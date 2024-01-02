package com.susu.domain.usecase

import com.susu.core.common.runCatchingIgnoreCancelled
import com.susu.domain.repository.LoginRepository
import com.susu.domain.repository.TokenRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val loginRepository: LoginRepository,
    private val tokenRepository: TokenRepository,
) {
    suspend operator fun invoke() = runCatchingIgnoreCancelled {
        loginRepository.logout()
        tokenRepository.deleteTokens()
    }
}
