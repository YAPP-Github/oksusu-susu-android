package com.susu.domain.usecase.ledger

import com.susu.core.common.runCatchingIgnoreCancelled
import com.susu.domain.repository.LedgerRepository
import java.time.LocalDateTime
import javax.inject.Inject

class GetLedgerUseCase @Inject constructor(
    private val ledgerRepository: LedgerRepository,
) {
    suspend operator fun invoke(id: Long) = runCatchingIgnoreCancelled {
        ledgerRepository.getLedger(
            id = id,
        )
    }
}
