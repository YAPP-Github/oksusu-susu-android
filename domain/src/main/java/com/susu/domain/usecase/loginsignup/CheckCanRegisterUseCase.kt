package com.susu.domain.usecase.loginsignup

import com.susu.core.common.runCatchingIgnoreCancelled
import com.susu.domain.repository.SignUpRepository
import javax.inject.Inject

class CheckCanRegisterUseCase @Inject constructor(
    private val signUpRepository: SignUpRepository,
) {
    suspend operator fun invoke(provider: String, oauthAccessToken: String) = runCatchingIgnoreCancelled {
        signUpRepository.canRegister(provider = provider, oauthAccessToken = oauthAccessToken)
    }
}
