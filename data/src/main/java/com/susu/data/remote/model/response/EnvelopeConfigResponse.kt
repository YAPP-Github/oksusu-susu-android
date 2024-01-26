package com.susu.data.remote.model.response

import com.susu.core.model.StatisticsOption
import kotlinx.serialization.Serializable

@Serializable
data class EnvelopeConfigResponse(
    val categories: List<CategoryConfigResponse>,
    val relationships: List<RelationshipConfigResponse>,
)

fun EnvelopeConfigResponse.toModel() = StatisticsOption(
    categories = categories.map { it.toModel() },
    relationships = relationships.map { it.toModel() },
)
