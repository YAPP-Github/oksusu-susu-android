package com.susu.data.remote.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EnvelopesHistoryListResponse(
    @SerialName("data")
    val envelopesHistoryList: List<EnvelopeHistoryResponse>,
    val page: Int = 0,
    val size: Int,
    val sort: Sort,
    val totalCount: Int,
    val totalPage: Int
)

internal fun EnvelopesHistoryListResponse.toModel() = this.envelopesHistoryList.map { envelopesHistory ->
    envelopesHistory.toModel()
}
