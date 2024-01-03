package com.susu.domain.usecase

import com.susu.core.common.runCatchingIgnoreCancelled
import com.susu.domain.repository.SignUpRepository
import javax.inject.Inject

class CheckCanRegisterUseCase @Inject constructor(
    private val signUpRepository: SignUpRepository,
) {
    suspend operator fun invoke(oauthAccessToken: String) = runCatchingIgnoreCancelled {
        signUpRepository.canRegister(oauthAccessToken = oauthAccessToken)
    }
}
