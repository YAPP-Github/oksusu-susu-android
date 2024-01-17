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
                fromStartAt = fromStartAt ?: LocalDateTime.now().minusYears(10),
                toEndAt = toEndAt ?: LocalDateTime.now().plusYears(10),
                page = page,
                sort = sort,
            )
        }
    }

    data class Param(
        val title: String? = null,
        val categoryId: Long? = null,
        val fromStartAt: LocalDateTime? = null,
        val toEndAt: LocalDateTime? = null,
        val page: Int? = null,
        val sort: String? = null,
    )
}
