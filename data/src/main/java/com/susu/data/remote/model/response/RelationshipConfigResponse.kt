package com.susu.data.remote.model.response

import com.susu.core.model.Relationship
import kotlinx.serialization.Serializable

@Serializable
data class RelationshipConfigResponse(
    val id: Int,
    val relation: String,
    val description: String = "",
)

fun RelationshipConfigResponse.toModel() = Relationship(
    id = id,
    relation = relation,
    description = description,
)
