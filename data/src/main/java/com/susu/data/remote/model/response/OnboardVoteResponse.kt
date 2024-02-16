package com.susu.data.remote.model.response

import kotlinx.serialization.Serializable

@Serializable
data class OnboardVoteResponse(
    val options: List<OnboardVoteOption>,
)

@Serializable
data class OnboardVoteOption(
    val content: String = "",
    val count: Int = 0,
)
