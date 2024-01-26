package com.susu.data.remote.model.response

import com.susu.core.model.Envelope
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class EnvelopeResponse(
    val id: Long,
    val uid: Long,
    val type: String,
    val friend: FriendInfo,
    val amount: Long,
    val gift: String? = null,
    val memo: String? = null,
    val hasVisited: Boolean? = null,
    val handedOverAt: LocalDateTime? = null,
)

internal fun EnvelopeResponse.toModel() = Envelope(
    id = id,
    uid = uid,
    type = type,
    friend = friend.toModel(),
    amount = amount,
    gift = gift,
    memo = memo,
    hasVisited = hasVisited,
    handedOverAt = handedOverAt?.toJavaLocalDateTime(),
)
