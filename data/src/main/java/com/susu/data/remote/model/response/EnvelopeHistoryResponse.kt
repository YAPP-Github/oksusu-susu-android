package com.susu.data.remote.model.response

import com.susu.core.model.EnvelopeSearch
import com.susu.core.model.Relationship
import kotlinx.serialization.Serializable

@Serializable
data class EnvelopeHistoryResponse(
    val envelope: EnvelopeInfo,
    val category: CategoryInfo? = null,
    val friend: FriendInfo? = null,
    val relation: RelationshipInfo? = null,
)

internal fun RelationshipInfo.toModel() = Relationship(
    id = id,
    relation = relation,
)

internal fun EnvelopeHistoryResponse.toModel() = EnvelopeSearch(
    envelope = envelope.toModel(),
    category = category?.toModel(),
    friend = friend?.toModel(),
    relationship = relation?.toModel(),
)
