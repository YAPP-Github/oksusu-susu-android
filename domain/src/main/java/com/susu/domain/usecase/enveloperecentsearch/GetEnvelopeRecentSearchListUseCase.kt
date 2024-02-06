package com.susu.domain.usecase.enveloperecentsearch

import com.susu.core.common.runCatchingIgnoreCancelled
import com.susu.domain.RECENT_SEARCH_MAX_COUNT
import com.susu.domain.repository.EnvelopeRecentSearchRepository
import javax.inject.Inject

class GetEnvelopeRecentSearchListUseCase @Inject constructor(
    private val envelopeRecentSearchRepository: EnvelopeRecentSearchRepository,
) {
    suspend operator fun invoke() = runCatchingIgnoreCancelled {
        envelopeRecentSearchRepository.getSearchList(RECENT_SEARCH_MAX_COUNT)
    }
}
