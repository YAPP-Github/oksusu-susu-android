package com.susu.core.model

import androidx.compose.runtime.Stable

@Stable
data class RecentSearch(
    val search: String,
    val saveTime: Long,
)
