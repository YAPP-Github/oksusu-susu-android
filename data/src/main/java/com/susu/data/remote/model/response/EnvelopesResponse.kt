package com.susu.data.remote.model.response

import com.susu.core.model.Envelope
import com.susu.core.model.Friend
import kotlinx.serialization.Serializable

@Serializable
data class EnvelopesResponse(
    val friend: FriendInfo,
    val receivedAmounts: Int,
    val sentAmounts: Int,
    val totalAmounts: Int,
)

@Serializable
data class FriendInfo(
    val id: Int,
    val name: String,
    val phoneNumber: String,
)

internal fun FriendInfo.toModel() = Friend(
    id = id,
    name = name,
    phoneNumber = phoneNumber,
)

internal fun EnvelopesResponse.toModel() = Envelope(
    friend = friend.toModel(),
    receivedAmounts = receivedAmounts,
    sentAmounts = sentAmounts,
    totalAmounts = totalAmounts,
)
