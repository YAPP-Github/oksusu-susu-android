package com.susu.domain.usecase.enveloperecentsearch

import com.susu.core.common.runCatchingIgnoreCancelled
import com.susu.domain.RECENT_SEARCH_MAX_COUNT
import com.susu.domain.repository.EnvelopeRecentSearchRepository
import javax.inject.Inject

class UpsertEnvelopeRecentSearchUseCase @Inject constructor(
    private val envelopeRecentSearchRepository: EnvelopeRecentSearchRepository,
) {
    suspend operator fun invoke(searchKeyword: String) = runCatchingIgnoreCancelled {
        with(envelopeRecentSearchRepository) {
            upsert(searchKeyword)
            val searchListCount = getSearchListCount()
            if (searchListCount > RECENT_SEARCH_MAX_COUNT) {
                deleteOldestSearchKeyword(searchListCount - RECENT_SEARCH_MAX_COUNT)
            }
            getSearchList(RECENT_SEARCH_MAX_COUNT)
        }
    }
}
