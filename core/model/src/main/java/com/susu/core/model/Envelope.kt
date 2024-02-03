package com.susu.core.model

import androidx.compose.runtime.Stable
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toKotlinLocalDateTime
import kotlinx.serialization.Serializable

@Stable
@Serializable
data class Envelope(
    val id: Long = 0,
    val uid: Long = 0,
    val type: String = "",
    val amount: Long = 0,
    val gift: String? = null,
    val memo: String? = null,
    val hasVisited: Boolean? = null,
    val handedOverAt: LocalDateTime = java.time.LocalDateTime.now().toKotlinLocalDateTime(),
    val friend: Friend = Friend(),
    val relationship: Relationship = Relationship(),
)
