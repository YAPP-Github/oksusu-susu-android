package com.susu.core.model

import androidx.compose.runtime.Stable
import kotlinx.serialization.Serializable

@Stable
@Serializable
data class EnvelopeDetail(
    val envelope: Envelope = Envelope(),
    val category: Category = Category(),
    val relationship: Relationship = Relationship(),
    val friendRelationship: FriendRelationship = FriendRelationship(),
    val friend: Friend = Friend(),
)
