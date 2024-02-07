package com.susu.core.model

import androidx.compose.runtime.Stable

@Stable
data class FriendStatistics(
    val friend: Friend = Friend(),
    val receivedAmounts: Int = 0,
    val sentAmounts: Int = 0,
    val totalAmounts: Int = 0,
)
