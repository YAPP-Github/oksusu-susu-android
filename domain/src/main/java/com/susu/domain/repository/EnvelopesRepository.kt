package com.susu.domain.repository

import com.susu.core.model.Category
import com.susu.core.model.FriendStatistics
import com.susu.core.model.Envelope
import com.susu.core.model.EnvelopeSearch
import com.susu.core.model.Friend
import com.susu.core.model.Relationship
import kotlinx.datetime.LocalDateTime

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

    suspend fun getEnvelopesHistoryList(
        friendIds: List<Long>?,
        ledgerId: Int?,
        type: List<String>?,
        include: List<String>?,
        fromAmount: Int?,
        toAmount: Int?,
        page: Int?,
        size: Int?,
        sort: String?,
    ): List<EnvelopeSearch>
}
