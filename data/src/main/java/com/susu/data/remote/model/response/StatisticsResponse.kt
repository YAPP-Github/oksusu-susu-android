package com.susu.data.remote.model.response

import com.susu.core.model.MyStatistics
import com.susu.core.model.SusuStatistics
import kotlinx.serialization.Serializable

@Serializable
data class MyStatisticsResponse(
    val highestAmountReceived: StatisticsElement = StatisticsElement(),
    val highestAmountSent: StatisticsElement = StatisticsElement(),
    val mostCategory: StatisticsElement = StatisticsElement(),
    val mostRelationship: StatisticsElement = StatisticsElement(),
    val mostSpentMonth: Long = 0L,
    val recentSpent: List<StatisticsElement> = emptyList(),
)

@Serializable
data class StatisticsElement(
    val title: String = "",
    val value: Long = 0L,
)

@Serializable
data class SusuStatisticsResponse(
    val averageSent: Long = 0L,
    val averageRelationship: StatisticsElement = StatisticsElement(),
    val averageCategory: StatisticsElement = StatisticsElement(),
    val recentSpent: List<StatisticsElement> = emptyList(),
    val mostSpentMonth: Long = 0L,
    val mostRelationship: StatisticsElement = StatisticsElement(),
    val mostCategory: StatisticsElement = StatisticsElement(),
)

fun MyStatisticsResponse.toModel() = MyStatistics(
    highestAmountReceived = highestAmountReceived.toModel(),
    highestAmountSent = highestAmountSent.toModel(),
    mostCategory = mostCategory.toModel(),
    mostRelationship = mostRelationship.toModel(),
    mostSpentMonth = mostSpentMonth,
    recentSpent = recentSpent.map { it.toModel() },
    recentMaximumSpent = recentSpent.maxOfOrNull { it.value } ?: 0L,
    recentTotalSpent = recentSpent.sumOf { it.value },
)

fun StatisticsElement.toModel() = com.susu.core.model.StatisticsElement(
    title = title,
    value = value,
)

fun SusuStatisticsResponse.toModel() = SusuStatistics(
    averageSent = averageSent,
    averageRelationship = averageRelationship.toModel(),
    averageCategory = averageCategory.toModel(),
    recentSpent = recentSpent.map { it.toModel() },
    mostSpentMonth = mostSpentMonth,
    mostRelationship = mostRelationship.toModel(),
    mostCategory = mostCategory.toModel(),
    recentMaximumSpent = recentSpent.maxOfOrNull { it.value } ?: 0,
    recentTotalSpent = recentSpent.sumOf { it.value },
)
