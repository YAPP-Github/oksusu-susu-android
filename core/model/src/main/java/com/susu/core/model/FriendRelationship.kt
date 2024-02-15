package com.susu.core.model

import androidx.compose.runtime.Stable
import kotlinx.serialization.Serializable

@Stable
@Serializable
data class FriendRelationship(
    val id: Long = 0,
    val friendId: Long = 0,
    val relationshipId: Long = 0,
    val customRelation: String? = null,
)
