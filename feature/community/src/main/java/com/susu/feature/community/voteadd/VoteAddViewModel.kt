package com.susu.feature.community.voteadd

import androidx.lifecycle.viewModelScope
import com.susu.core.model.Category
import com.susu.core.ui.base.BaseViewModel
import com.susu.domain.usecase.categoryconfig.GetCategoryConfigUseCase
import com.susu.domain.usecase.vote.CreateVoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class VoteAddViewModel @Inject constructor(
    private val getCategoryConfigUseCase: GetCategoryConfigUseCase,
    private val createVoteUseCase: CreateVoteUseCase,
) : BaseViewModel<VoteAddState, VoteAddSideEffect>(
    VoteAddState(),
) {
    companion object {
        private const val MIN_OPTION_COUNT = 2
        private const val MAX_OPTION_COUNT = 5
    }

    fun createVote() = viewModelScope.launch {
        createVoteUseCase(
            param = CreateVoteUseCase.Param(
                content = currentState.content,
                optionList = currentState.voteOptionStateList.map { it.content },
                categoryId = currentState.selectedCategory.id,
            ),
        ).onSuccess {
            Timber.tag("테스트").d("$it")
            popBackStack()
        }.onFailure {
            postSideEffect(VoteAddSideEffect.HandleException(it, ::createVote))
        }
    }

    fun getCategoryConfig() = viewModelScope.launch {
        if (currentState.categoryConfigList.isNotEmpty()) return@launch

        getCategoryConfigUseCase()
            .onSuccess { categoryConfig ->
                intent {
                    copy(
                        categoryConfigList = categoryConfig.toPersistentList(),
                        selectedCategory = categoryConfig.first(),
                    )
                }
            }
    }

    fun popBackStack() = postSideEffect(VoteAddSideEffect.PopBackStack)
    fun selectCategory(category: Category) = intent {
        copy(selectedCategory = category)
    }

    fun updateContent(content: String) = intent {
        copy(content = content)
    }

    fun updateOptionContent(index: Int, content: String) = intent {
        copy(
            voteOptionStateList = voteOptionStateList.mapIndexed { voteIndex, voteOptionState ->
                if (index == voteIndex) voteOptionState.copy(content = content)
                else voteOptionState
            }.toPersistentList(),
        )
    }

    fun toggleOptionSavedState(index: Int) = intent {
        copy(
            voteOptionStateList = voteOptionStateList.mapIndexed { voteIndex, voteOptionState ->
                if (index == voteIndex) voteOptionState.copy(isSaved = voteOptionState.isSaved.not())
                else voteOptionState
            }.toPersistentList(),
        )
    }

    fun removeOptionState(index: Int) = intent {
        if (voteOptionStateList.size <= MIN_OPTION_COUNT) return@intent this
        copy(
            voteOptionStateList = voteOptionStateList.removeAt(index),
        )
    }

    fun addOptionState() = intent {
        if (voteOptionStateList.size >= MAX_OPTION_COUNT) return@intent this
        copy(
            voteOptionStateList = voteOptionStateList.add(VoteOptionState()),
        )
    }
}
