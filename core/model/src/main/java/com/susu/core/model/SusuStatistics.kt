package com.susu.core.model

import androidx.compose.runtime.Stable

@Stable
data class SusuStatistics(
    val averageSent: Long = 0L,
    val averageRelationship: StatisticsElement = StatisticsElement(),
    val averageCategory: StatisticsElement = StatisticsElement(),
    val recentSpent: List<StatisticsElement> = emptyList(),
    val recentTotalSpent: Long = 0L,
    val recentMaximumSpent: Long = 0L,
    val mostSpentMonth: Long = 0L,
    val mostRelationship: StatisticsElement = StatisticsElement(),
    val mostCategory: StatisticsElement = StatisticsElement(),
)
