package com.susu.core.model

data class MyStatistics(
    val highestAmountReceived: StatisticsElement = StatisticsElement(),
    val highestAmountSent: StatisticsElement = StatisticsElement(),
    val mostCategory: StatisticsElement = StatisticsElement(),
    val mostRelationship: StatisticsElement = StatisticsElement(),
    val mostSpentMonth: Int = 0,
    val recentSpent: List<StatisticsElement> = emptyList(),
)
