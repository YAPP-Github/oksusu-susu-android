package com.susu.domain.usecase.statistics

import com.susu.core.common.runCatchingIgnoreCancelled
import com.susu.domain.repository.StatisticsRepository
import javax.inject.Inject

class GetStatisticsOptionUseCase @Inject constructor(
    private val statisticsRepository: StatisticsRepository,
) {
    suspend operator fun invoke() = runCatchingIgnoreCancelled {
        statisticsRepository.getStatisticOptionConfig()
    }
}
