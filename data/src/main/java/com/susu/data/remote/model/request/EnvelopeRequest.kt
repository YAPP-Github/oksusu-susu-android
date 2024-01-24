package com.susu.data.remote.model.request

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class EnvelopeRequest(
    val type: String,
    val friendId: Long,
    val ledgerId: Long? = null,
    val amount: Long,
    val gift: String? = null,
    val memo: String? = null,
    val hasVisited: Boolean? = null,
    val handedOverAt: LocalDateTime? = null,
    val category: CategoryRequest? = null,
)

@Serializable
data class CategoryRequest(
    val id: Long,
    val customCategory: String? = null
)
