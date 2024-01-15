package com.susu.data.remote.model.response

import com.susu.core.model.Category
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LedgerListResponse(
    @SerialName("data")
    val ledgerList: List<Ledger>,
    val page: Int,
    val size: Int,
    val totalPage: Int,
    val totalCount: Int,
    val sort: SortInfo,
)

@Serializable
data class Ledger(
    @SerialName("ledger")
    val info: LedgerInfo,
    val category: CategoryInfo,
    val totalAmounts: Int,
    val totalCounts: Int,
)

@Serializable
data class LedgerInfo(
    val id: Int,
    val title: String,
    val description: String,
    val startAt: LocalDateTime,
    val endAt: LocalDateTime,
)

@Serializable
data class CategoryInfo(
    val id: Int,
    val seq: Int,
    val category: String,
    val customCategory: String? = null,
)

@Serializable
data class SortInfo(
    val empty: Boolean,
    val unsorted: Boolean,
    val sorted: Boolean,
)

internal fun LedgerListResponse.toModel() = this.ledgerList.map { ledger ->
    with(ledger) {
        com.susu.core.model.Ledger(
            id = info.id,
            title = info.title,
            description = info.description,
            startAt = info.startAt.toJavaLocalDateTime(),
            endAt = info.endAt.toJavaLocalDateTime(),
            category = category.toModel(),
            totalAmounts = totalAmounts,
            totalCounts = totalCounts,
        )
    }
}

internal fun CategoryInfo.toModel() = Category(
    id = id,
    seq = seq,
    category = category,
    customCategory = customCategory,
)
