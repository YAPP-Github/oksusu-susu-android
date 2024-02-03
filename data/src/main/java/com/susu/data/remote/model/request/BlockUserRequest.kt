package com.susu.data.remote.model.request

import kotlinx.serialization.Serializable

@Serializable
data class BlockUserRequest(
    val targetId: Long,
    val targetType: String = "USER",
)
