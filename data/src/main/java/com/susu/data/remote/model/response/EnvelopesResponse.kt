package com.susu.data.remote.model.response

import com.susu.core.model.EnvelopeStatics
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
    val uid: Int,
    val createdAt: String,
    val modifiedAt: String,
    val name: String,
    val phoneNumber: String,
)

internal fun FriendInfo.toModel() = Friend(
    id = id,
    uid = uid,
    name = name,
    phoneNumber = phoneNumber,
    createdAt = createdAt,
    modifiedAt = modifiedAt,
)

internal fun EnvelopesResponse.toModel() = EnvelopeStatics(
    friend = friend.toModel(),
    receivedAmounts = receivedAmounts,
    sentAmounts = sentAmounts,
    totalAmounts = totalAmounts,
)
