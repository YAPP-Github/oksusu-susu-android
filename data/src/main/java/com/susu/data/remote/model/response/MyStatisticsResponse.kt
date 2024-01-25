package com.susu.data.remote.model.response

import com.susu.core.model.MyStatistics
import kotlinx.serialization.Serializable

@Serializable
data class MyStatisticsResponse(
    val highestAmountReceived: StatisticsElement,
    val highestAmountSent: StatisticsElement,
    val mostCategory: StatisticsElement,
    val mostRelationship: StatisticsElement,
    val mostSpentMonth: Int,
    val recentSpent: List<StatisticsElement>,
)

@Serializable
data class StatisticsElement(
    val title: String,
    val value: Int,
)

fun MyStatisticsResponse.toModel() = MyStatistics(
    highestAmountReceived = highestAmountReceived.toModel(),
    highestAmountSent = highestAmountSent.toModel(),
    mostCategory = mostCategory.toModel(),
    mostRelationship = mostRelationship.toModel(),
    mostSpentMonth = mostSpentMonth,
    recentSpent = recentSpent.map { it.toModel() },
    recentMaximumSpent = recentSpent.maxOfOrNull { it.value } ?: 0,
    recentTotalSpent = recentSpent.sumOf { it.value }
)

fun StatisticsElement.toModel() = com.susu.core.model.StatisticsElement(
    title = title,
    value = value,
)
