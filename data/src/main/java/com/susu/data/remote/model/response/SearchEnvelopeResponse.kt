package com.susu.data.remote.model.response

import com.susu.core.model.Relationship
import com.susu.core.model.SearchEnvelope
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class SearchEnvelopeResponse(
    val data: List<SearchEnvelopeData>,
)

@Serializable
data class SearchEnvelopeData(
    val envelope: Envelope,
    val category: CategoryInfo,
    val friend: FriendInfo,
    val relationship: RelationInfo,
    val friendRelationship: FriendRelationship,
)

@Serializable
data class Envelope(
    val id: Long,
    val uid: Long,
    val type: String,
    val amount: Long,
    val gift: String? = null,
    val memo: String? = null,
    val hasVisited: Boolean? = null,
    val handedOverAt: LocalDateTime,
)

@Serializable
data class RelationInfo(
    val id: Long,
    val relation: String,
)

@Serializable
data class FriendRelationship(
    val id: Long,
    val friendId: Long,
    val relationshipId: Long,
    val customRelation: String? = null,
)

internal fun SearchEnvelopeResponse.toModel() = data.map {
    SearchEnvelope(
        envelope = com.susu.core.model.Envelope(
            id = it.envelope.id,
            uid = it.envelope.uid,
            type = it.envelope.type,
            friend = it.friend.toModel(),
            relationship = Relationship(
                id = it.relationship.id,
                relation = it.relationship.relation,
                customRelation = it.friendRelationship.customRelation,
            ),
            amount = it.envelope.amount,
            gift = it.envelope.gift,
            memo = it.envelope.memo,
            hasVisited = it.envelope.hasVisited,
            handedOverAt = it.envelope.handedOverAt,
        ),
        category = it.category.toModel(),
        friend = it.friend.toModel(),
        relation = Relationship(
            id = it.relationship.id,
            relation = it.relationship.relation,
            customRelation = it.friendRelationship.customRelation,
        ),
    )
}
