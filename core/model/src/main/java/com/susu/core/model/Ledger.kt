package com.susu.core.model

import androidx.compose.runtime.Stable
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toKotlinLocalDateTime
import kotlinx.serialization.Serializable

@Stable
@Serializable
data class Ledger(
    val id: Int = 0,
    val title: String = "",
    val description: String = "",
    val startAt: LocalDateTime = java.time.LocalDateTime.now().toKotlinLocalDateTime(),
    val endAt: LocalDateTime = java.time.LocalDateTime.now().toKotlinLocalDateTime(),
    val category: Category = Category(),
    val totalAmounts: Int = 0,
    val totalCounts: Int = 0,
)
