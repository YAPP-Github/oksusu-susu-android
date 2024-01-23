package com.susu.data.data.repository

import com.susu.core.model.Ledger
import com.susu.data.remote.api.LedgerService
import com.susu.data.remote.model.request.toData
import com.susu.data.remote.model.response.toModel
import com.susu.domain.repository.LedgerRepository
import kotlinx.datetime.toKotlinLocalDateTime
import java.time.LocalDateTime
import javax.inject.Inject

class LedgerRepositoryImpl @Inject constructor(
    private val ledgerService: LedgerService,
) : LedgerRepository {
    override suspend fun getLedgerList(
        title: String?,
        categoryIdList: List<Int>?,
        fromStartAt: LocalDateTime,
        toEndAt: LocalDateTime,
        page: Int?,
        sort: String?,
    ): List<Ledger> = ledgerService.getLedgerList(
        title = title,
        categoryIdList = categoryIdList,
        fromStartAt = fromStartAt.toKotlinLocalDateTime(),
        toEndAt = toEndAt.toKotlinLocalDateTime(),
        page = page,
        sort = sort,
    ).getOrThrow().toModel()

    override suspend fun createLedger(ledger: Ledger): Ledger = ledgerService.createLedger(
        ledgerRequest = ledger.toData(),
    ).getOrThrow().toModel()

    override suspend fun editLedger(ledger: Ledger): Ledger = ledgerService.editLedger(
        id = ledger.id,
        ledgerRequest = ledger.toData(),
    ).getOrThrow().toModel()

    override suspend fun deleteLedger(id: Int) = ledgerService.deleteLedgerList(
        listOf(id),
    ).getOrThrow()
}
