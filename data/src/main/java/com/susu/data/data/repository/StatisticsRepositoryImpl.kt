package com.susu.data.data.repository

import com.susu.data.remote.api.StatisticsService
import com.susu.data.remote.model.response.toModel
import com.susu.domain.repository.StatisticsRepository
import javax.inject.Inject

class StatisticsRepositoryImpl @Inject constructor(
    private val statisticsService: StatisticsService,
) : StatisticsRepository {
    override suspend fun getMyStatistics() = statisticsService.getMyStatistics().getOrThrow().toModel()
}
