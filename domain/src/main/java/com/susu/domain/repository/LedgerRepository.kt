package com.susu.domain.repository

import com.susu.core.model.Ledger
import java.time.LocalDateTime

interface LedgerRepository {
    suspend fun getLedgerList(
        title: String?,
        categoryIdList: List<Int>?,
        fromStartAt: LocalDateTime,
        toEndAt: LocalDateTime,
        page: Int?,
        sort: String?,
    ): List<Ledger>

    suspend fun createLedger(
        ledger: Ledger,
    ): Ledger

    suspend fun editLedger(
        ledger: Ledger,
    ): Ledger

    suspend fun deleteLedger(
        id: Int,
    )
}
