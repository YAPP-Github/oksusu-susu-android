package com.susu.core.model

import kotlinx.serialization.Serializable

@Serializable
data class Friend(
    val id: Long = 0,
    val uid: Long = 0,
    val name: String = "",
    val phoneNumber: String = "",
    val createdAt: String = "",
    val modifiedAt: String = "",
)
