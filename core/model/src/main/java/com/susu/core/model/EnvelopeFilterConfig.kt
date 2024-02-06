package com.susu.core.model

data class EnvelopeFilterConfig(
    val minReceivedAmount: Long,
    val maxReceivedAmount: Long,
    val minSentAmount: Long,
    val maxSentAmount: Long,
)
