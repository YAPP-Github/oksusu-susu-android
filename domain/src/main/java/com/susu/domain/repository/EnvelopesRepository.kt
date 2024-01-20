package com.susu.domain.repository

import com.susu.core.model.Envelope

interface EnvelopesRepository {
    suspend fun getEnvelopesList(
        friendIds: List<Int>?,
        fromTotalAmounts: Int?,
        toTotalAmounts: Int?,
        page: Int?,
        size: Int?,
        sort: String?,
    ): List<Envelope>
}
