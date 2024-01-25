package com.susu.data.data.repository

import com.susu.core.model.MyStatistics
import com.susu.core.model.StatisticsElement
import com.susu.data.remote.api.StatisticsService
import com.susu.data.remote.model.response.toModel
import com.susu.domain.repository.StatisticsRepository
import javax.inject.Inject

class StatisticsRepositoryImpl @Inject constructor(
    private val statisticsService: StatisticsService,
) : StatisticsRepository {
    override suspend fun getMyStatistics(): MyStatistics {
        val originalStatistic = statisticsService.getMyStatistics().getOrThrow().toModel()
        val sortedRecentSpent = originalStatistic.recentSpent.sortedBy { it.title.toInt() }
            .map { StatisticsElement(title = it.title.substring(it.title.length - 2).toInt().toString(), value = it.value) }

        return MyStatistics(
            highestAmountReceived = originalStatistic.highestAmountReceived,
            highestAmountSent = originalStatistic.highestAmountSent,
            mostCategory = originalStatistic.mostCategory,
            mostRelationship = originalStatistic.mostRelationship,
            mostSpentMonth = originalStatistic.mostSpentMonth % 100,
            recentSpent = sortedRecentSpent,
            recentTotalSpent = originalStatistic.recentTotalSpent,
            recentMaximumSpent = originalStatistic.recentMaximumSpent,
        )
    }
}
