package com.susu.data.data.repository

import com.susu.core.model.Ledger
import com.susu.data.remote.api.LedgerService
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
        categoryId: Long?,
        fromStartAt: LocalDateTime,
        toEndAt: LocalDateTime,
        page: Int?,
        sort: String?,
    ): List<Ledger> {
        return ledgerService.getLedgerList(
            title = title,
            categoryId = categoryId,
            fromStartAt = fromStartAt.toKotlinLocalDateTime(),
            toEndAt = toEndAt.toKotlinLocalDateTime(),
            page = page,
            sort = sort,
        ).getOrThrow().toModel()
    }
}
