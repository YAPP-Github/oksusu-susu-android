package com.susu.core.model

import java.time.LocalDateTime


data class FriendSearch(
    val friend: Friend,
    val relationship: Relationship,
    val recentEnvelope: RecentEnvelope? = null,
)

data class RecentEnvelope(
    val category: String,
    val handedOverAt: LocalDateTime
)
