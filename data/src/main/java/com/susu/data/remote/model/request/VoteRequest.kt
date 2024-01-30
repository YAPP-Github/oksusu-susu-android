package com.susu.data.remote.model.request

import kotlinx.serialization.Serializable

@Serializable
data class VoteRequest(
    val isCancel: Boolean,
    val optionId: Long,
)
