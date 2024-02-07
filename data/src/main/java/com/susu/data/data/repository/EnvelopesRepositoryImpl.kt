package com.susu.data.data.repository

import com.susu.core.model.Envelope
import com.susu.core.model.EnvelopeDetail
import com.susu.core.model.EnvelopeFilterConfig
import com.susu.core.model.EnvelopeSearch
import com.susu.core.model.FriendStatistics
import com.susu.core.model.Relationship
import com.susu.core.model.SearchEnvelope
import com.susu.data.remote.api.EnvelopesService
import com.susu.data.remote.model.request.CategoryRequest
import com.susu.data.remote.model.request.EnvelopeRequest
import com.susu.data.remote.model.response.toModel
import com.susu.domain.repository.EnvelopesRepository
import kotlinx.datetime.LocalDateTime
import javax.inject.Inject

class EnvelopesRepositoryImpl @Inject constructor(
    private val envelopesService: EnvelopesService,
) : EnvelopesRepository {
    override suspend fun getEnvelopesList(
        friendIds: List<Long>?,
        fromTotalAmounts: Long?,
        toTotalAmounts: Long?,
        page: Int?,
        size: Int?,
        sort: String?,
    ): List<FriendStatistics> = envelopesService.getEnvelopesList(
        friendIds = friendIds,
        fromTotalAmounts = fromTotalAmounts,
        toTotalAmounts = toTotalAmounts,
        page = page,
        size = size,
        sort = sort,
    ).getOrThrow().toModel()

    override suspend fun getRelationShipConfigList(): List<Relationship> = envelopesService
        .getRelationShipConfigList()
        .getOrThrow()
        .toModel()

    override suspend fun createEnvelope(
        type: String,
        friendId: Long,
        ledgerId: Long?,
        amount: Long,
        gift: String?,
        memo: String?,
        hasVisited: Boolean?,
        handedOverAt: kotlinx.datetime.LocalDateTime,
        categoryId: Long?,
        customCategory: String?,
    ): Envelope = envelopesService.createEnvelope(
        envelopeRequest = EnvelopeRequest(
            type = type,
            friendId = friendId,
            ledgerId = ledgerId,
            amount = amount,
            gift = gift,
            memo = memo,
            hasVisited = hasVisited,
            handedOverAt = handedOverAt,
            category = if (categoryId != null) {
                CategoryRequest(
                    id = categoryId,
                    customCategory = customCategory,
                )
            } else {
                null
            },
        ),
    ).getOrThrow().toModel()

    override suspend fun searchEnvelope(
        friendIds: List<Int>?,
        ledgerId: Long?,
        types: String?,
        fromAmount: Long?,
        toAmount: Long?,
        page: Int?,
        size: Int?,
        sort: String?,
    ): List<SearchEnvelope> = envelopesService.searchEnvelope(
        friendIds = friendIds,
        ledgerId = ledgerId,
        types = types,
        fromAmount = fromAmount,
        toAmount = toAmount,
        page = page,
        size = size,
        sort = sort,
    ).getOrThrow().toModel()

    override suspend fun getEnvelopesHistoryList(
        friendIds: List<Long>?,
        ledgerId: Int?,
        type: List<String>?,
        include: List<String>?,
        fromAmount: Int?,
        toAmount: Int?,
        page: Int?,
        size: Int?,
        sort: String?,
    ): List<EnvelopeSearch> = envelopesService.getEnvelopesHistoryList(
        friendIds = friendIds,
        ledgerId = ledgerId,
        types = type,
        include = include,
        fromAmount = fromAmount,
        toAmount = toAmount,
        page = page,
        size = size,
        sort = sort,
    ).getOrThrow().toModel()

    override suspend fun getEnvelope(id: Long): Envelope = envelopesService.getEnvelope(id).getOrThrow().toModel()

    override suspend fun deleteEnvelope(id: Long) = envelopesService.deleteEnvelope(id).getOrThrow()

    override suspend fun editEnvelope(
        id: Long,
        type: String,
        friendId: Long,
        ledgerId: Long?,
        amount: Long,
        gift: String?,
        memo: String?,
        hasVisited: Boolean?,
        handedOverAt: LocalDateTime,
        categoryId: Long?,
        customCategory: String?,
    ): Envelope = envelopesService.editEnvelope(
        id = id,
        envelopeRequest = EnvelopeRequest(
            type = type,
            friendId = friendId,
            ledgerId = ledgerId,
            amount = amount,
            gift = gift,
            memo = memo,
            hasVisited = hasVisited,
            handedOverAt = handedOverAt,
            category = if (categoryId != null) {
                CategoryRequest(
                    id = categoryId,
                    customCategory = customCategory,
                )
            } else {
                null
            },
        ),
    ).getOrThrow().toModel()

    override suspend fun getEnvelopeFilterConfig(): EnvelopeFilterConfig =
        envelopesService
            .getEnvelopeFilterConfig()
            .getOrThrow()
            .toModel()

    override suspend fun getEnvelopeDetail(
        id: Long,
    ): EnvelopeDetail = envelopesService.getEnvelopeDetail(
        id = id,
    ).getOrThrow().toModel()
}
