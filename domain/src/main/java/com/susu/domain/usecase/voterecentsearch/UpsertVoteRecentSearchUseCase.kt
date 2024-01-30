package com.susu.domain.usecase.voterecentsearch

import com.susu.core.common.runCatchingIgnoreCancelled
import com.susu.domain.RECENT_SEARCH_MAX_COUNT
import com.susu.domain.repository.VoteRecentSearchRepository
import javax.inject.Inject

class UpsertVoteRecentSearchUseCase @Inject constructor(
    private val voteRecentSearchRepository: VoteRecentSearchRepository,
) {
    suspend operator fun invoke(searchKeyword: String) = runCatchingIgnoreCancelled {
        with(voteRecentSearchRepository) {
            upsert(searchKeyword)
            val searchListCount = getSearchListCount()
            if (searchListCount > RECENT_SEARCH_MAX_COUNT) {
                deleteOldestSearchKeyword(searchListCount - RECENT_SEARCH_MAX_COUNT)
            }
            getSearchList(RECENT_SEARCH_MAX_COUNT)
        }
    }
}
