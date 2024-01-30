package com.susu.feature.community.votesearch

import com.susu.core.model.Vote
import com.susu.core.ui.base.SideEffect
import com.susu.core.ui.base.UiState
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

data class VoteSearchState(
    val searchKeyword: String = "",
    val recentSearchKeywordList: PersistentList<String> = persistentListOf(),
    val voteList: PersistentList<Vote> = persistentListOf(),
    val isLoading: Boolean = false,
    val showSearchResultEmpty: Boolean = false,
) : UiState

sealed interface VoteSearchSideEffect : SideEffect {
    data object PopBackStack : VoteSearchSideEffect
    data class NavigateVoteDetail(val vote: Vote) : VoteSearchSideEffect
    data object FocusClear : VoteSearchSideEffect
}
