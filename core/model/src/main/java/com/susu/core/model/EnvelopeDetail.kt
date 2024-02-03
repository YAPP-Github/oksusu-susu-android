package com.susu.core.model

data class EnvelopeDetail(
    val envelope: Envelope,
    val category: Category,
    val relationship: Relationship,
    val friendRelationship: FriendRelationship,
    val friend: Friend,
)
