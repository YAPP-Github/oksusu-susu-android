package com.susu.data.remote.model.request

import kotlinx.serialization.Serializable

@Serializable
data class EditVoteRequest(
    val boardId: Long,
    val content: String,
)
