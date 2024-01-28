package com.susu.domain.usecase.ledger

import com.susu.core.common.runCatchingIgnoreCancelled
import com.susu.domain.repository.LedgerRepository
import javax.inject.Inject

typealias OnlyStartAtCategoryIdList = List<Int>

class GetCreateLedgerConfigUseCase @Inject constructor(
    private val ledgerRepository: LedgerRepository,
) {
    suspend operator fun invoke(): Result<OnlyStartAtCategoryIdList> = runCatchingIgnoreCancelled {
        ledgerRepository.getCreateLedgerConfig()
    }
}
