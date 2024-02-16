package com.susu.data.data.repository

import com.susu.core.model.OnboardVote
import com.susu.data.remote.api.OnboardService
import com.susu.domain.repository.OnboardRepository
import javax.inject.Inject

class OnboardRepositoryImpl @Inject constructor(
    private val onboardService: OnboardService,
) : OnboardRepository {
    override suspend fun getOnboardVote(): OnboardVote {
        val result = onboardService.getOnboardVote().getOrThrow().options
        val mostOption = result.maxBy { it.count }

        return OnboardVote(
            mostContent = mostOption.content,
            mostPercentage = (mostOption.count.toFloat() / result.sumOf { it.count } * 100).toInt(),
        )
    }
}
