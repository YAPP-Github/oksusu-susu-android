package com.susu.data.remote.model.response

import kotlinx.serialization.Serializable

@Serializable
data class VoteListResponse(
    val data: List<VoteResponse> = emptyList(),
)

internal fun VoteListResponse.toModel() = data.map { it.toModel() }
