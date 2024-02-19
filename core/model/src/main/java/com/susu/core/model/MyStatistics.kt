package com.susu.core.model

import androidx.compose.runtime.Stable

@Stable
data class MyStatistics(
    val highestAmountReceived: StatisticsElement = StatisticsElement(),
    val highestAmountSent: StatisticsElement = StatisticsElement(),
    val mostCategory: StatisticsElement = StatisticsElement(),
    val mostRelationship: StatisticsElement = StatisticsElement(),
    val mostSpentMonth: Int = 0,
    val recentSpent: List<StatisticsElement> = emptyList(),
    val recentTotalSpent: Int = 0,
    val recentMaximumSpent: Int = 0,
) {
    companion object {
        val EMPTY = MyStatistics()
    }
}
