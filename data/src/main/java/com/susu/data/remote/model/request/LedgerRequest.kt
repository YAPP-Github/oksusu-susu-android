package com.susu.data.remote.model.request

import com.susu.core.model.Ledger
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class LedgerRequest(
    val title: String,
    val description: String = "",
    val categoryId: Int,
    val customCategory: String?,
    val startAt: LocalDateTime,
    val endAt: LocalDateTime,
)

internal fun Ledger.toData() = LedgerRequest(
    title = this.title,
    categoryId = category.id,
    customCategory = category.customCategory,
    startAt = startAt,
    endAt = endAt,
)
