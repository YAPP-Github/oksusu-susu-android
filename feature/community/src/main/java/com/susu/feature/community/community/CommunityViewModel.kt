package com.susu.feature.community.community

import androidx.lifecycle.viewModelScope
import com.susu.core.model.Category
import com.susu.core.model.Vote
import com.susu.core.ui.base.BaseViewModel
import com.susu.core.ui.extension.decodeFromUri
import com.susu.domain.usecase.categoryconfig.GetCategoryConfigUseCase
import com.susu.domain.usecase.vote.GetPopularVoteListUseCase
import com.susu.domain.usecase.vote.GetVoteListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.serialization.json.Json
import javax.inject.Inject

@HiltViewModel
class CommunityViewModel @Inject constructor(
    private val getVoteListUseCase: GetVoteListUseCase,
    private val getCategoryConfigUseCase: GetCategoryConfigUseCase,
    private val getPopularVoteListUseCase: GetPopularVoteListUseCase,
) : BaseViewModel<CommunityState, CommunitySideEffect>(
    CommunityState(),
) {
    private val mutex = Mutex()

    private var page = 0
    private var isLast = false
    private var isFirstVisit = true

    fun addVoteIfNeed(vote: String?) {
        val toAddVote = vote?.let {
            Json.decodeFromUri<Vote>(vote)
        } ?: return

        if (toAddVote in currentState.voteList) return

        if (currentState.selectedCategory != null &&
            currentState.selectedCategory?.id != currentState.categoryConfigList.find { it.name == toAddVote.category }?.id
        ) {
            return
        }

        if (currentState.isCheckedVotePopular) return

        intent {
            copy(
                voteList = currentState
                    .voteList
                    .add(0, toAddVote),
            )
        }
    }

    fun initData() {
        if (isFirstVisit.not()) return
        getVoteList()
        isFirstVisit = false
    }

    fun getCategoryConfig() = viewModelScope.launch {
        if (currentState.categoryConfigList.isNotEmpty()) return@launch

        getCategoryConfigUseCase()
            .onSuccess { categoryConfig ->
                intent {
                    copy(
                        categoryConfigList = categoryConfig.toPersistentList(),
                    )
                }
            }
    }

    fun getVoteList(needClear: Boolean = false) = viewModelScope.launch {
        mutex.withLock {
            val currentList = if (needClear) {
                page = 0
                isLast = false
                emptyList()
            } else {
                currentState.voteList
            }

            if (isLast) return@launch

            getVoteListUseCase(
                GetVoteListUseCase.Param(
                    page = page,
                    content = null,
                    mine = currentState.isCheckShowMine,
                    sortType = if (currentState.isCheckedVotePopular) "POPULAR" else "LATEST",
                    categoryId = currentState.selectedCategory?.id,
                    sort = null,
                ),
            ).onSuccess { voteList ->
                isLast = voteList.isEmpty()
                page++
                val newVoteList = currentList.plus(voteList).toPersistentList()
                intent {
                    copy(
                        voteList = newVoteList,
                    )
                }
            }
        }
    }

    fun getPopularVoteList() = viewModelScope.launch {
        if (currentState.popularVoteList.isNotEmpty()) return@launch

        getPopularVoteListUseCase()
            .onSuccess {
                intent { copy(popularVoteList = it.toPersistentList()) }
            }
            .onFailure {
                postSideEffect(CommunitySideEffect.HandleException(it, ::getPopularVoteList))
            }
    }

    fun selectCategory(category: Category?) {
        intent {
            copy(selectedCategory = category)
        }

        getVoteList(true)
    }

    fun toggleShowMyVote() {
        intent {
            copy(isCheckShowMine = !isCheckShowMine)
        }

        getVoteList(true)
    }

    fun toggleShowVotePopular() {
        intent {
            copy(isCheckedVotePopular = !isCheckedVotePopular)
        }

        getVoteList(true)
    }
}
