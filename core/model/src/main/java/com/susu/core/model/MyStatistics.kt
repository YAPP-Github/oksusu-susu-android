package com.susu.core.model

import androidx.compose.runtime.Stable

@Stable
data class MyStatistics(
    val highestAmountReceived: StatisticsElement = StatisticsElement(),
    val highestAmountSent: StatisticsElement = StatisticsElement(),
    val mostCategory: StatisticsElement = StatisticsElement(),
    val mostRelationship: StatisticsElement = StatisticsElement(),
    val mostSpentMonth: Long = 0L,
    val recentSpent: List<StatisticsElement> = emptyList(),
    val recentTotalSpent: Long = 0L,
    val recentMaximumSpent: Long = 0L,
) {
    companion object {
        val EMPTY = MyStatistics()
    }
}
