package com.susu.domain.repository

import com.susu.core.model.FriendStatistics
import com.susu.core.model.Envelope
import com.susu.core.model.Relationship
import java.time.LocalDateTime

interface EnvelopesRepository {
    suspend fun getEnvelopesList(
        friendIds: List<Int>?,
        fromTotalAmounts: Int?,
        toTotalAmounts: Int?,
        page: Int?,
        size: Int?,
        sort: String?,
    ): List<FriendStatistics>

    suspend fun getRelationShipConfigList(): List<Relationship>

    suspend fun createEnvelope(
        type: String,
        friendId: Long,
        ledgerId: Long? = null,
        amount: Long,
        gift: String? = null,
        memo: String? = null,
        hasVisited: Boolean? = null,
        handedOverAt: LocalDateTime,
        categoryId: Long? = null,
        customCategory: String? = null,
    ): Envelope
}
