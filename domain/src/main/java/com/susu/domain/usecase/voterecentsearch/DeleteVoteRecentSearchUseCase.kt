package com.susu.domain.usecase.voterecentsearch

import com.susu.core.common.runCatchingIgnoreCancelled
import com.susu.domain.RECENT_SEARCH_MAX_COUNT
import com.susu.domain.repository.LedgerRecentSearchRepository
import com.susu.domain.repository.VoteRecentSearchRepository
import javax.inject.Inject

class DeleteVoteRecentSearchUseCase @Inject constructor(
    private val voteRecentSearchRepository: VoteRecentSearchRepository,
) {
    suspend operator fun invoke(searchKeyword: String) = runCatchingIgnoreCancelled {
        with(voteRecentSearchRepository) {
            deleteBySearchKeyword(searchKeyword)
            getSearchList(RECENT_SEARCH_MAX_COUNT)
        }
    }
}
