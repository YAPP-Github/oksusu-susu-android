package com.susu.data.remote.model.response

import com.susu.core.model.EnvelopeDetail
import com.susu.core.model.FriendRelationship
import kotlinx.serialization.Serializable

@Serializable
data class EnvelopeDetailResponse(
    val envelope: EnvelopeInfo,
    val category: CategoryInfo,
    val relationship: RelationshipInfo,
    val friendRelationship: FriendRelationshipInfo,
    val friend: FriendInfo,
)

@Serializable
data class FriendRelationshipInfo(
    val id: Long,
    val friendId: Long,
    val relationshipId: Long,
)

internal fun FriendRelationshipInfo.toModel() = FriendRelationship(
    id = id,
    friendId = friendId,
    relationshipId = relationshipId,
)

internal fun EnvelopeDetailResponse.toModel() = EnvelopeDetail(
    envelope = envelope.toModel(),
    category = category.toModel(),
    relationship = relationship.toModel(),
    friendRelationship = friendRelationship.toModel(),
    friend = friend.toModel(),
)
