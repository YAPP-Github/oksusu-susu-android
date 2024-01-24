package com.susu.domain.repository

import com.susu.core.model.Envelope
import com.susu.core.model.Relationship

interface EnvelopesRepository {
    suspend fun getEnvelopesList(
        friendIds: List<Int>?,
        fromTotalAmounts: Int?,
        toTotalAmounts: Int?,
        page: Int?,
        size: Int?,
        sort: String?,
    ): List<Envelope>

    suspend fun getRelationShipConfigList(): List<Relationship>
}
