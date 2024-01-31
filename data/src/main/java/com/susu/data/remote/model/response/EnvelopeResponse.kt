package com.susu.data.remote.model.response

import com.susu.core.model.Envelope
import com.susu.core.model.Relationship
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class EnvelopeResponse(
    val envelope: EnvelopeInfo,
    val friend: FriendInfo,
    val friendRelationship: FriendRelationShipInfo,
    val relationship: RelationshipInfo,
)

@Serializable
data class EnvelopeInfo(
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
data class FriendRelationShipInfo(
    val id: Long,
    val friendId: Long,
    val relationshipId: Long,
    val customRelation: String? = null,
)

@Serializable
data class RelationshipInfo(
    val id: Long,
    val relation: String,
)

internal fun EnvelopeResponse.toModel() = Envelope(
    id = envelope.id,
    uid = envelope.uid,
    type = envelope.type,
    amount = envelope.amount,
    gift = envelope.gift,
    memo = envelope.memo,
    hasVisited = envelope.hasVisited,
    handedOverAt = envelope.handedOverAt,
    friend = friend.toModel(),
    relationship = Relationship(
        id = relationship.id,
        relation = relationship.relation,
        customRelation = friendRelationship.customRelation,
    ),
)
