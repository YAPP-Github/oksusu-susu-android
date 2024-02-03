package com.susu.domain.usecase.statistics

import com.susu.core.common.runCatchingIgnoreCancelled
import com.susu.domain.repository.StatisticsRepository
import javax.inject.Inject

class GetSusuStatisticsUseCase @Inject constructor(
    private val statisticsRepository: StatisticsRepository,
) {
    suspend operator fun invoke(age: String, relationshipId: Int, categoryId: Int) = runCatchingIgnoreCancelled {
        statisticsRepository.getSusuStatistics(age, relationshipId, categoryId)
    }
}
