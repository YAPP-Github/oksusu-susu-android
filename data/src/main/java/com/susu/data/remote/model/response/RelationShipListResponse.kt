package com.susu.data.remote.model.response

import com.susu.core.model.Category
import com.susu.core.model.RelationShip
import kotlinx.serialization.Serializable

@Serializable
data class RelationShipListResponse(
    val relationships: List<RelationConfigShipResponse> = emptyList(),
)

@Serializable
data class RelationConfigShipResponse(
    val id: Int,
    val relation: String,
)

internal fun RelationShipListResponse.toModel() = relationships.map { it.toModel() }

internal fun RelationConfigShipResponse.toModel() = RelationShip(
    id = id,
    relation = relation,
)
