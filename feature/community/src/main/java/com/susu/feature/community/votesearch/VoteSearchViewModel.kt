package com.susu.feature.community.votesearch

import androidx.lifecycle.viewModelScope
import com.susu.core.model.Vote
import com.susu.core.ui.base.BaseViewModel
import com.susu.domain.usecase.vote.GetVoteListUseCase
import com.susu.domain.usecase.voterecentsearch.DeleteVoteRecentSearchUseCase
import com.susu.domain.usecase.voterecentsearch.GetVoteRecentSearchListUseCase
import com.susu.domain.usecase.voterecentsearch.UpsertVoteRecentSearchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VoteSearchViewModel @Inject constructor(
    private val upsertVoteRecentSearchUseCase: UpsertVoteRecentSearchUseCase,
    private val getVoteRecentSearchListUseCase: GetVoteRecentSearchListUseCase,
    private val deleteVoteRecentSearchUseCase: DeleteVoteRecentSearchUseCase,
    private val getVoteListUseCase: GetVoteListUseCase,
) : BaseViewModel<VoteSearchState, VoteSearchSideEffect>(
    VoteSearchState(),
) {
    fun navigateVoteDetail(vote: Vote) = postSideEffect(VoteSearchSideEffect.NavigateVoteDetail(vote.id))

    fun getVoteRecentSearchList() = viewModelScope.launch {
        getVoteRecentSearchListUseCase()
            .onSuccess(::updateRecentSearchList)
            .onFailure { }
    }

    fun deleteVoteRecentSearch(search: String) = viewModelScope.launch {
        deleteVoteRecentSearchUseCase(search)
            .onSuccess(::updateRecentSearchList)
            .onFailure { }
    }

    fun upsertVoteRecentSearch(search: String) = viewModelScope.launch {
        upsertVoteRecentSearchUseCase(search)
            .onSuccess(::updateRecentSearchList)
            .onFailure { }
    }

    fun clearFocus() = postSideEffect(VoteSearchSideEffect.FocusClear)

    fun hideSearchResultEmpty() = intent { copy(showSearchResultEmpty = false) }

    fun updateSearch(search: String) = intent {
        copy(
            searchKeyword = search,
            voteList = if (search.isBlank()) persistentListOf() else voteList,
        )
    }

    fun getVoteList(search: String) = viewModelScope.launch {
        if (search.isBlank()) return@launch

        getVoteListUseCase(GetVoteListUseCase.Param(content = search))
            .onSuccess {
                intent {
                    copy(
                        voteList = it.toPersistentList(),
                        showSearchResultEmpty = it.isEmpty(),
                    )
                }
            }
    }

    fun popBackStack() = postSideEffect(VoteSearchSideEffect.PopBackStack)

    private fun updateRecentSearchList(searchList: List<String>) = intent {
        copy(
            recentSearchKeywordList = searchList.toPersistentList(),
        )
    }
}
