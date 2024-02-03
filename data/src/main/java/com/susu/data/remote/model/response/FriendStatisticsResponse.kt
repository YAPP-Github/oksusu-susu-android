package com.susu.data.remote.model.response

import com.susu.core.model.Friend
import com.susu.core.model.FriendStatistics
import kotlinx.serialization.Serializable

@Serializable
data class FriendStatisticsResponse(
    val friend: FriendInfo,
    val receivedAmounts: Int,
    val sentAmounts: Int,
    val totalAmounts: Int,
)

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

internal fun FriendStatisticsResponse.toModel() = FriendStatistics(
    friend = friend.toModel(),
    receivedAmounts = receivedAmounts,
    sentAmounts = sentAmounts,
    totalAmounts = totalAmounts,
)
