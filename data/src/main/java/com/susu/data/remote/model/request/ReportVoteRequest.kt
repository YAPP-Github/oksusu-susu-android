package com.susu.data.remote.model.request

import kotlinx.serialization.Serializable

@Serializable
data class ReportVoteRequest(
    val metadataId: Long = 1,
    val targetId: Long,
    val targetType: String = "POST",
)
