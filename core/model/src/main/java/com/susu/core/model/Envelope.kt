package com.susu.core.model

data class Envelope(
    val friend: Friend = Friend(),
    val receivedAmounts: Int = 0,
    val sentAmounts: Int = 0,
    val totalAmounts: Int = 0,
)
