package com.susu.domain.usecase.ledgerrecentsearch

import com.susu.core.common.runCatchingIgnoreCancelled
import com.susu.domain.RECENT_SEARCH_MAX_COUNT
import com.susu.domain.repository.LedgerRecentSearchRepository
import javax.inject.Inject

class GetLedgerRecentSearchListUseCase @Inject constructor(
    private val ledgerRecentSearchRepository: LedgerRecentSearchRepository,
) {
    suspend operator fun invoke() = runCatchingIgnoreCancelled {
        ledgerRecentSearchRepository.getSearchList(RECENT_SEARCH_MAX_COUNT)
    }
}
