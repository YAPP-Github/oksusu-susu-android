package com.susu.feature.community.voteedit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.susu.core.model.Category
import com.susu.core.model.Vote
import com.susu.core.ui.base.BaseViewModel
import com.susu.core.ui.extension.decodeFromUri
import com.susu.domain.usecase.vote.EditVoteUseCase
import com.susu.domain.usecase.vote.GetPostCategoryConfigUseCase
import com.susu.feature.community.VOTE_CONTENT_MAX_LENGTH
import com.susu.feature.community.navigation.CommunityRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import javax.inject.Inject

@HiltViewModel
class VoteEditViewModel @Inject constructor(
    private val getPostCategoryConfigUseCase: GetPostCategoryConfigUseCase,
    private val editVoteUseCase: EditVoteUseCase,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<VoteEditState, VoteEditSideEffect>(
    VoteEditState(),
) {
    private val argument = savedStateHandle.get<String>(CommunityRoute.VOTE_ARGUMENT_NAME)!!
    private var voteId: Long = 0
    private var isFirstVisit = true

    fun initData() {
        if (isFirstVisit.not()) return

        Json.decodeFromUri<Vote>(argument).let { vote ->
            voteId = vote.id
            intent {
                copy(
                    voteOptionStateList = vote.optionList.map { it.content }.toPersistentList(),
                    content = vote.content,
                    selectedBoardId = vote.boardId,
                )
            }
        }

        isFirstVisit = false
    }

    fun editVote() = viewModelScope.launch {
        intent { copy(isLoading = true) }
        editVoteUseCase(
            param = EditVoteUseCase.Param(
                content = currentState.content.trim(),
                boardId = currentState.selectedBoardId,
                id = voteId,
            ),
        ).onSuccess {
            postSideEffect(VoteEditSideEffect.PopBackStack)
        }.onFailure {
            postSideEffect(VoteEditSideEffect.HandleException(it, ::editVote))
        }
        intent { copy(isLoading = false) }
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

    fun popBackStack() = postSideEffect(VoteEditSideEffect.PopBackStack)

    fun selectCategory(category: Category) = intent {
        copy(selectedBoardId = category.id.toLong())
    }

    fun updateContent(content: String) = intent {
        if (content.length > VOTE_CONTENT_MAX_LENGTH) {
            postSideEffect(VoteEditSideEffect.ShowInvalidContentSnackbar)
            return@intent this
        }

        copy(content = content)
    }

    fun showCannotChangeOptionSnackbar() = postSideEffect(VoteEditSideEffect.ShowCanNotChangeOptionSnackbar)
}
