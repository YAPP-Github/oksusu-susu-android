package com.susu.data.remote.model.response

import com.susu.core.model.Friend
import kotlinx.serialization.Serializable

@Serializable
data class FriendInfo(
    val id: Long,
    val name: String,
    val phoneNumber: String = "",
)

internal fun FriendInfo.toModel() = Friend(
    id = id,
    name = name,
    phoneNumber = phoneNumber,
)
