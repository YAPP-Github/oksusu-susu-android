package com.susu.domain.usecase.ledgerrecentsearch

import com.susu.core.common.runCatchingIgnoreCancelled
import com.susu.domain.RECENT_SEARCH_MAX_COUNT
import com.susu.domain.repository.LedgerRecentSearchRepository
import javax.inject.Inject

class UpsertLedgerRecentSearchUseCase @Inject constructor(
    private val ledgerRecentSearchRepository: LedgerRecentSearchRepository,
) {
    suspend operator fun invoke(searchKeyword: String) = runCatchingIgnoreCancelled {
        with(ledgerRecentSearchRepository) {
            upsert(searchKeyword)
            val searchListCount = getSearchListCount()
            if (searchListCount > RECENT_SEARCH_MAX_COUNT) {
                deleteOldestSearchKeyword(searchListCount - RECENT_SEARCH_MAX_COUNT)
            }
            getSearchList(RECENT_SEARCH_MAX_COUNT)
        }
    }
}
