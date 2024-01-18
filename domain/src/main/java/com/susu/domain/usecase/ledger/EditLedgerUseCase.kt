package com.susu.domain.usecase.ledger

import com.susu.core.common.runCatchingIgnoreCancelled
import com.susu.core.model.Ledger
import com.susu.domain.repository.LedgerRepository
import javax.inject.Inject

class EditLedgerUseCase @Inject constructor(
    private val ledgerRepository: LedgerRepository,
) {
    suspend operator fun invoke(ledger: Ledger) = runCatchingIgnoreCancelled {
        ledgerRepository.editLedger(ledger)
    }
}
