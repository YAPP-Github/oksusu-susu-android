package com.susu.domain.repository

import com.susu.core.model.FriendStatistics
import com.susu.core.model.Envelope
import com.susu.core.model.EnvelopeDetail
import com.susu.core.model.EnvelopeSearch
import com.susu.core.model.Relationship
import com.susu.core.model.SearchEnvelope
import kotlinx.datetime.LocalDateTime

interface EnvelopesRepository {
    suspend fun getEnvelopesList(
        friendIds: List<Long>?,
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

    suspend fun getEnvelopeDetail(
        id: Long,
    ): EnvelopeDetail

    suspend fun searchEnvelope(
        friendIds: List<Int>?,
        ledgerId: Long?,
        types: String?,
        fromAmount: Long?,
        toAmount: Long?,
        page: Int? = null,
        size: Int? = null,
        sort: String? = null,
    ): List<SearchEnvelope>

    suspend fun getEnvelope(
        id: Long,
    ): Envelope

    suspend fun deleteEnvelope(
        id: Long,
    )

    suspend fun editEnvelope(
        id: Long,
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
