package com.susu.domain.usecase.ledger

import com.susu.core.common.runCatchingIgnoreCancelled
import com.susu.domain.repository.LedgerRepository
import java.time.LocalDateTime
import javax.inject.Inject

class GetLedgerListUseCase @Inject constructor(
    private val ledgerRepository: LedgerRepository,
) {
    suspend operator fun invoke(param: Param) = runCatchingIgnoreCancelled {
        with(param) {
            ledgerRepository.getLedgerList(
                title = title,
                categoryId = categoryId,
                fromStartAt = fromStartAt,
                toEndAt = toEndAt,
                page = page,
                sort = sort,
            )
        }
    }

    data class Param(
        val title: String? = null,
        val categoryId: Long? = null,
        val fromStartAt: LocalDateTime = LocalDateTime.now().minusYears(10),
        val toEndAt: LocalDateTime = LocalDateTime.now(),
        val page: Int? = null,
        val sort: String? = null,
    )
}
