package com.susu.data.remote.model.response

import com.susu.core.model.EnvelopeFilterConfig
import kotlinx.serialization.Serializable

@Serializable
data class EnvelopeFilterConfigResponse(
    val minReceivedAmount: Long,
    val maxReceivedAmount: Long,
    val minSentAmount: Long,
    val maxSentAmount: Long,
)

internal fun EnvelopeFilterConfigResponse.toModel() = EnvelopeFilterConfig(
    minReceivedAmount = minReceivedAmount,
    maxReceivedAmount = maxReceivedAmount,
    minSentAmount = minSentAmount,
    maxSentAmount = maxSentAmount,
)
