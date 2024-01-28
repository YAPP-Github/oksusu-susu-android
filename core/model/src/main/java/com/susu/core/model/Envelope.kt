package com.susu.core.model

import kotlinx.datetime.LocalDateTime

data class Envelope(
    val id: Long = 0,
    val uid: Long = 0,
    val type: String = "",
    val friend: Friend = Friend(),
    val amount: Long = 0,
    val gift: String? = null,
    val memo: String? = null,
    val hasVisited: Boolean? = null,
    val handedOverAt: LocalDateTime? = null,
)
