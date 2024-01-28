package com.susu.data.remote.model.response

import com.susu.core.model.FriendSearch
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class FriendSearchListResponse(
    val data: List<FriendSearchResponse>,
)

@Serializable
data class FriendSearchResponse(
    val friend: Friend,
    val relationship: Relationship,
    val recentEnvelope: RecentEnvelope? = null,
)

@Serializable
data class Friend(
    val id: Long,
    val name: String,
    val phoneNumber: String? = null,
)

@Serializable
data class Relationship(
    val id: Int,
    val relation: String,
    val customRelation: String? = null,
)

@Serializable
data class RecentEnvelope(
    val category: String,
    val handedOverAt: LocalDateTime,
)

internal fun FriendSearchResponse.toModel() = FriendSearch(
    friend = com.susu.core.model.Friend(
        id = friend.id,
        name = friend.name,
        phoneNumber = friend.phoneNumber ?: "",
    ),
    relationship = com.susu.core.model.Relationship(
        id = relationship.id,
        relation = relationship.relation,
        customRelation = relationship.customRelation,
    ),
    recentEnvelope = recentEnvelope?.toModel(),
)

internal fun RecentEnvelope.toModel() = com.susu.core.model.RecentEnvelope(
    category = category,
    handedOverAt = handedOverAt.toJavaLocalDateTime(),
)
