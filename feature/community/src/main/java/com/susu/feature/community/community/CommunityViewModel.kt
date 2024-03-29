package com.susu.feature.community.community

import androidx.lifecycle.viewModelScope
import com.susu.core.model.Category
import com.susu.core.model.Vote
import com.susu.core.model.exception.AlreadyExistsReportHistoryException
import com.susu.core.model.exception.CannotBlockMyselfException
import com.susu.core.ui.base.BaseViewModel
import com.susu.core.ui.extension.decodeFromUri
import com.susu.domain.usecase.block.BlockUserUseCase
import com.susu.domain.usecase.report.ReportVoteUseCase
import com.susu.domain.usecase.vote.GetPopularVoteListUseCase
import com.susu.domain.usecase.vote.GetPostCategoryConfigUseCase
import com.susu.domain.usecase.vote.GetVoteListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.Job
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.serialization.json.Json
import javax.inject.Inject

@HiltViewModel
class CommunityViewModel @Inject constructor(
    private val getVoteListUseCase: GetVoteListUseCase,
    private val getPostCategoryConfigUseCase: GetPostCategoryConfigUseCase,
    private val getPopularVoteListUseCase: GetPopularVoteListUseCase,
    private val reportVoteUseCase: ReportVoteUseCase,
    private val blockUserUseCase: BlockUserUseCase,
) : BaseViewModel<CommunityState, CommunitySideEffect>(
    CommunityState(),
) {
    private val mutex = Mutex()

    private var page = 0
    private var isLast = false
    private var isFirstVisit = true
    fun logSearchIconClickEvent() = postSideEffect(CommunitySideEffect.LogSearchIconClickEvent)
    fun logPopularVoteClickEvent() = postSideEffect(CommunitySideEffect.LogPopularVoteClickEvent)
    fun logShowMyVoteClickEvent() = postSideEffect(CommunitySideEffect.LogShowMyVoteClickEvent)
    fun logAlignPopularVoteClickEvent() = postSideEffect(CommunitySideEffect.LogAlignPopularVoteClickEvent)
    fun logCategoryClickEvent(name: String) = postSideEffect(CommunitySideEffect.LogCategoryClickEvent(name))

    fun addVoteIfNeed(vote: String?) {
        val toAddVote = vote?.let {
            Json.decodeFromUri<Vote>(vote)
        } ?: return

        if (toAddVote.id in currentState.voteList.map { it.id }) return

        if (currentState.selectedCategory != null &&
            currentState.selectedCategory?.id != currentState.categoryConfigList.find { it.name == toAddVote.boardName }?.id
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

    fun updateVoteIfNeed(vote: String?) {
        val toUpdateVote = vote?.let {
            Json.decodeFromUri<Vote>(vote)
        } ?: return

        intent {
            copy(
                voteList = currentState
                    .voteList
                    .map {
                        if (it.id == toUpdateVote.id) {
                            toUpdateVote
                        } else {
                            it
                        }
                    }
                    .distinctBy { it.id }
                    .toPersistentList(),
            )
        }
    }

    fun deleteVoteIfNeed(toDeleteVoteId: Long?) {
        if (toDeleteVoteId == null) return

        intent {
            copy(
                voteList = voteList
                    .filter { it.id != toDeleteVoteId }
                    .distinctBy { it.id }
                    .toPersistentList(),
                popularVoteList = popularVoteList
                    .filter { it.id != toDeleteVoteId }
                    .distinctBy { it.id }
                    .toPersistentList(),
            )
        }
    }

    fun needRefreshIfNeed(needRefresh: Boolean) {
        if (needRefresh.not()) return

        getVoteList(true)
    }

    fun initData() {
        if (isFirstVisit.not()) return
        getVoteList()
        isFirstVisit = false
    }

    fun getCategoryConfig() = viewModelScope.launch {
        if (currentState.categoryConfigList.isNotEmpty()) return@launch

        getPostCategoryConfigUseCase()
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
                val newVoteList = currentList.plus(voteList).distinctBy { it.id }.toPersistentList()
                intent {
                    copy(
                        voteList = newVoteList,
                    )
                }
            }
        }
    }

    fun getPopularVoteList() = viewModelScope.launch {
        getPopularVoteListUseCase()
            .onSuccess {
                intent { copy(popularVoteList = it.toPersistentList()) }
            }
            .onFailure {
                postSideEffect(CommunitySideEffect.HandleException(it, ::getPopularVoteList))
            }
    }

    fun refreshData(onFinish: () -> Unit = {}) = viewModelScope.launch {
        joinAll(getVoteList(true), getPopularVoteList())
        onFinish()
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

    fun navigateVoteAdd() = postSideEffect(CommunitySideEffect.NavigateVoteAdd)

    fun navigateVoteDetail(id: Long) = postSideEffect(CommunitySideEffect.NavigateVoteDetail(id))
    fun navigateVoteSearch() = postSideEffect(CommunitySideEffect.NavigateVoteSearch)

    fun showReportDialog(vote: Vote) = postSideEffect(
        CommunitySideEffect.ShowReportDialog(
            onConfirmRequest = { reportVote(vote.id) },
            onCheckedAction = { blockUser(vote.uid) },
        ),
    )

    private fun reportVote(voteId: Long): Job = viewModelScope.launch {
        reportVoteUseCase(voteId)
            .onFailure { throwable ->
                when (throwable) {
                    is AlreadyExistsReportHistoryException -> postSideEffect(CommunitySideEffect.ShowSnackbar(throwable.message))
                    else -> postSideEffect(CommunitySideEffect.HandleException(throwable = throwable, retry = { reportVote(voteId) }))
                }
            }
    }

    private fun blockUser(uid: Long): Job = viewModelScope.launch {
        blockUserUseCase(uid)
            .onSuccess {
                getVoteList(true)
            }
            .onFailure { throwable ->
                when (throwable) {
                    is CannotBlockMyselfException -> postSideEffect(CommunitySideEffect.ShowSnackbar(throwable.message))
                    else -> postSideEffect(CommunitySideEffect.HandleException(throwable = throwable, retry = { blockUser(uid) }))
                }
            }
    }
}
