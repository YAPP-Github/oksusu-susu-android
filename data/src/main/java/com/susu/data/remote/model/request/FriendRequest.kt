package com.susu.data.remote.model.request

import kotlinx.serialization.Serializable

@Serializable
data class FriendRequest(
    val name: String,
    val phoneNumber: String? = null,
    val relationshipId: Long,
    val customRelation: String? = null,
)
