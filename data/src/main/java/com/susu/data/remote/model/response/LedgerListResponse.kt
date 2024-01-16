package com.susu.data.remote.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LedgerListResponse(
    @SerialName("data")
    val ledgerList: List<LedgerResponse>,
    val page: Int,
    val size: Int,
    val totalPage: Int,
    val totalCount: Int,
    val sort: SortInfo,
)

@Serializable
data class SortInfo(
    val empty: Boolean,
    val unsorted: Boolean,
    val sorted: Boolean,
)

internal fun LedgerListResponse.toModel() = this.ledgerList.map { ledger ->
    ledger.toModel()
}
