package com.susu.core.model

import androidx.compose.runtime.Stable

@Stable
data class SusuStatistics(
    val averageSent: Int = 0,
    val averageRelationship: StatisticsElement = StatisticsElement(),
    val averageCategory: StatisticsElement = StatisticsElement(),
    val recentSpent: List<StatisticsElement> = emptyList(),
    val recentTotalSpent: Int = 0,
    val recentMaximumSpent: Int = 0,
    val mostSpentMonth: Int = 0,
    val mostRelationship: StatisticsElement = StatisticsElement(),
    val mostCategory: StatisticsElement = StatisticsElement(),
)
