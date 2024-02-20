package com.susu.core.model

import androidx.compose.runtime.Stable

@Stable
data class StatisticsElement(
    val title: String = "",
    val value: Long = 0L,
)
