package com.susu.domain.usecase.ledger

import com.susu.core.common.runCatchingIgnoreCancelled
import com.susu.domain.repository.LedgerRepository
import javax.inject.Inject

class DeleteLedgerUseCase @Inject constructor(
    private val ledgerRepository: LedgerRepository,
) {
    suspend operator fun invoke(id: Long) = runCatchingIgnoreCancelled {
        ledgerRepository.deleteLedger(id)
    }
}
