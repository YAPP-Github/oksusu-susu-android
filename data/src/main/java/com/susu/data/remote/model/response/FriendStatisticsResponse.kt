package com.susu.data.remote.model.response

import com.susu.core.model.FriendStatistics
import kotlinx.serialization.Serializable

@Serializable
data class FriendStatisticsResponse(
    val friend: FriendInfo,
    val receivedAmounts: Int,
    val sentAmounts: Int,
    val totalAmounts: Int,
)

internal fun FriendStatisticsResponse.toModel() = FriendStatistics(
    friend = friend.toModel(),
    receivedAmounts = receivedAmounts,
    sentAmounts = sentAmounts,
    totalAmounts = totalAmounts,
)
