package com.susu.domain.usecase.loginsignup

import com.susu.core.common.runCatchingIgnoreCancelled
import com.susu.domain.repository.OnboardRepository
import javax.inject.Inject

class GetOnboardVoteUseCase @Inject constructor(
    private val onboardRepository: OnboardRepository,
) {

    suspend operator fun invoke() = runCatchingIgnoreCancelled {
        onboardRepository.getOnboardVote()
    }
}
