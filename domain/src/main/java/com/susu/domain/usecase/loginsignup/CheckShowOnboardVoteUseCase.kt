package com.susu.domain.usecase.loginsignup

import com.susu.core.common.runCatchingIgnoreCancelled
import com.susu.domain.repository.SignUpRepository
import javax.inject.Inject

class CheckShowOnboardVoteUseCase @Inject constructor(
    private val signUpRepository: SignUpRepository,
) {
    suspend operator fun invoke() = runCatchingIgnoreCancelled {
        signUpRepository.getShowOnboardVote()
    }.getOrNull()
}
