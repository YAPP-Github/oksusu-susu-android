package com.susu.data.remote.model.response

import com.susu.core.model.Envelope
import com.susu.core.model.EnvelopeSearch
import com.susu.core.model.Relationship
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class SearchEnvelopeResponse(
    val envelope: EnvelopeInfo,
    val category: CategoryInfo? = null,
    val friend: FriendInfo? = null,
    val relation: RelationshipInfo? = null,
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
    val handedOverAt: LocalDateTime? = null,
)

@Serializable
data class RelationshipInfo(
    val id: Int,
    val relation: String,
    val description: String = "",
)

internal fun EnvelopeInfo.toModel() = Envelope(
    id = id,
    uid = uid,
    type = type,
    amount = amount,
    gift = gift,
    memo = memo,
    hasVisited = hasVisited,
    handedOverAt = handedOverAt?.toJavaLocalDateTime(),
)

internal fun RelationshipInfo.toModel() = Relationship(
    id = id,
    relation = relation,
    description = description,
)

internal fun SearchEnvelopeResponse.toModel() = EnvelopeSearch(
    envelope = envelope.toModel(),
    category = category?.toModel(),
    friend = friend?.toModel(),
    relationship = relation?.toModel(),
)
