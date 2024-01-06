package com.susu.domain.usecase.ledgerrecentsearch

import com.susu.core.common.runCatchingIgnoreCancelled
import com.susu.domain.LEDGER_SEARCH_MAX_COUNT
import com.susu.domain.repository.LedgerRecentSearchRepository
import javax.inject.Inject

class UpsertLedgerRecentSearchUseCase @Inject constructor(
    private val ledgerRecentSearchRepository: LedgerRecentSearchRepository,
) {
    suspend operator fun invoke(search: String) = runCatchingIgnoreCancelled {
        with(ledgerRecentSearchRepository) {
            upsert(search)
            val searchListCount = getSearchListCount()
            if (searchListCount > LEDGER_SEARCH_MAX_COUNT) {
                deleteOldestSearch(searchListCount - LEDGER_SEARCH_MAX_COUNT)
            }
            getSearchList(LEDGER_SEARCH_MAX_COUNT)
        }
    }
}
