package com.susu.core.model

import androidx.compose.runtime.Stable

@Stable
data class StatisticsOption(
    val categories: List<Category> = emptyList(),
    val relationships: List<Relationship> = emptyList(),
)
