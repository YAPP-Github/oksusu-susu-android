package com.susu.data.remote.model.response

import com.susu.core.model.Relationship
import kotlinx.serialization.Serializable

@Serializable
data class RelationShipListResponse(
    val relationships: List<RelationConfigShipResponse> = emptyList(),
)

@Serializable
data class RelationConfigShipResponse(
    val id: Long,
    val relation: String,
)

internal fun RelationShipListResponse.toModel() = relationships.map { it.toModel() }

internal fun RelationConfigShipResponse.toModel() = Relationship(
    id = id,
    relation = relation,
)
