package com.susu.core.model

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class Envelope(
    val id: Long = 0,
    val uid: Long = 0,
    val type: String = "",
    val amount: Long = 0,
    val gift: String? = null,
    val memo: String? = null,
    val hasVisited: Boolean? = null,
    val handedOverAt: LocalDateTime? = null,
    val friend: Friend = Friend(),
    val relationship: Relationship = Relationship(),
)
