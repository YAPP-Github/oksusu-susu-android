package com.susu.core.model

import java.time.LocalDateTime

data class Ledger(
    val id: Int,
    val title: String,
    val description: String,
    val startAt: LocalDateTime,
    val endAt: LocalDateTime,
    val category: Category,
    val totalAmounts: Int,
)
