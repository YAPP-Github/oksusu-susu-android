package com.susu.data.remote.model.response

import com.susu.core.model.Category
import com.susu.core.model.Ledger
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LedgerResponse(
    @SerialName("ledger")
    val info: LedgerInfo,
    val category: CategoryInfo,
    val totalAmounts: Int = 0,
    val totalCounts: Int = 0,
)

@Serializable
data class LedgerInfo(
    val id: Int,
    val title: String,
    val description: String = "",
    val startAt: LocalDateTime,
    val endAt: LocalDateTime,
)

@Serializable
data class CategoryInfo(
    val id: Int,
    val seq: Int,
    val category: String,
    val customCategory: String? = null,
    val style: String,
)

internal fun LedgerResponse.toModel() = Ledger(
    id = info.id,
    title = info.title,
    description = info.description,
    startAt = info.startAt,
    endAt = info.endAt,
    category = category.toModel(),
    totalAmounts = totalAmounts,
    totalCounts = totalCounts,
)

internal fun CategoryInfo.toModel() = Category(
    id = id,
    seq = seq,
    name = category,
    customCategory = customCategory,
    style = style,
)
