package com.susu.domain.usecase.ledgerrecentsearch

import com.susu.core.common.runCatchingIgnoreCancelled
import com.susu.domain.repository.LedgerRecentSearchRepository
import javax.inject.Inject

class GetLedgerRecentSearchListUseCase @Inject constructor(
    private val ledgerRecentSearchRepository: LedgerRecentSearchRepository,
) {
    suspend operator fun invoke(count: Int = 5) = runCatchingIgnoreCancelled {
        ledgerRecentSearchRepository.getSearchList(count)
    }
}
