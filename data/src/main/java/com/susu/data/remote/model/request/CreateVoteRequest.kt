package com.susu.data.remote.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateVoteRequest(
    val content: String,
    @SerialName("options")
    val optionList: List<VoteOption>,
    @SerialName("postCategoryId")
    val categoryId: Int
)

@Serializable
data class VoteOption(
    val content: String,
    val seq: Int
)
