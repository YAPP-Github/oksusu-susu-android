package com.susu.core.model

import androidx.compose.runtime.Stable

@Stable
data class OnboardVote(
    val mostContent: String,
    val mostPercentage: Int,
)
