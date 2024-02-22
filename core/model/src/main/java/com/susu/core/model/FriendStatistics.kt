package com.susu.core.model

import androidx.compose.runtime.Stable

@Stable
data class FriendStatistics(
    val friend: Friend = Friend(),
    val receivedAmounts: Long = 0,
    val sentAmounts: Long = 0,
    val totalAmounts: Long = 0,
)
