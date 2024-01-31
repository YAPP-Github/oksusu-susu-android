package com.susu.feature.community.voteedit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.susu.core.model.Category
import com.susu.core.model.Vote
import com.susu.core.ui.base.BaseViewModel
import com.susu.core.ui.extension.decodeFromUri
import com.susu.core.ui.extension.encodeToUri
import com.susu.domain.usecase.vote.CreateVoteUseCase
import com.susu.domain.usecase.vote.GetPostCategoryConfigUseCase
import com.susu.feature.community.navigation.CommunityRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import javax.inject.Inject

@HiltViewModel
class VoteEditViewModel @Inject constructor(
    private val getPostCategoryConfigUseCase: GetPostCategoryConfigUseCase,
    private val createVoteUseCase: CreateVoteUseCase,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<VoteEditState, VoteEditSideEffect>(
    VoteEditState(),
) {
    private val argument = savedStateHandle.get<String>(CommunityRoute.VOTE_ARGUMENT_NAME)!!
    private var isFirstVisit = true

    fun initData() {
        if (isFirstVisit.not()) return

        Json.decodeFromUri<Vote>(argument).let { vote ->
            intent {
                copy(
                    voteOptionStateList = vote.optionList.map { it.content }.toPersistentList(),
                    content = vote.content,
                    selectedCategory = Category() // TODO
                )
            }
        }

        isFirstVisit = false
    }

    fun editVote() = viewModelScope.launch {
        intent { copy(isLoading = true) }
        createVoteUseCase(
            param = CreateVoteUseCase.Param(
                content = currentState.content,
                optionList = currentState.voteOptionStateList,
                categoryId = currentState.selectedCategory.id,
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
                        selectedCategory = categoryConfig.first(),
                    )
                }
            }
    }

    fun popBackStack() = postSideEffect(VoteEditSideEffect.PopBackStack)

    fun selectCategory(category: Category) = intent {
        copy(selectedCategory = category)
    }

    fun updateContent(content: String) = intent {
        copy(content = content)
    }
}
