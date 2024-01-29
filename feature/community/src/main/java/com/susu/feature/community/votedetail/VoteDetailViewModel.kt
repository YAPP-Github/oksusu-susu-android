package com.susu.feature.community.votedetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.susu.core.model.Category
import com.susu.core.ui.base.BaseViewModel
import com.susu.core.ui.extension.encodeToUri
import com.susu.domain.usecase.vote.CreateVoteUseCase
import com.susu.domain.usecase.vote.GetPostCategoryConfigUseCase
import com.susu.domain.usecase.vote.GetVoteDetailUseCase
import com.susu.feature.community.navigation.CommunityRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class VoteDetailViewModel @Inject constructor(
    private val getVoteDetailUseCase: GetVoteDetailUseCase,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<VoteDetailState, VoteDetailSideEffect>(
    VoteDetailState(),
) {
    private val voteId = savedStateHandle.get<String>(CommunityRoute.VOTE_ID_ARGUMENT_NAME)!!.toLong()

    fun getVoteDetail() = viewModelScope.launch {
        intent { copy(isLoading = true) }
        getVoteDetailUseCase(
            voteId
        ).onSuccess {
            intent { copy(vote = it) }
        }.onFailure {
            postSideEffect(VoteDetailSideEffect.HandleException(it, ::getVoteDetail))
        }
        intent { copy(isLoading = false) }
    }

    fun popBackStack() = postSideEffect(VoteDetailSideEffect.PopBackStack)
}
