package com.susu.core.model

import androidx.compose.runtime.Stable
import java.time.LocalDateTime

@Stable
data class Ledger(
    val id: Int,
    val title: String,
    val description: String,
    val startAt: LocalDateTime,
    val endAt: LocalDateTime,
    val category: Category,
    val totalAmounts: Int,
    val totalCounts: Int,
)
