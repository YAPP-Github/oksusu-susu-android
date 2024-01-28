package com.susu.core.model

import java.time.LocalDateTime

data class Envelope(
    val id: Long,
    val uid: Long,
    val type: String,
    val friend: Friend,
    val amount: Long,
    val gift: String? = null,
    val memo: String? = null,
    val hasVisited: Boolean? = null,
    val handedOverAt: LocalDateTime? = null,
)
