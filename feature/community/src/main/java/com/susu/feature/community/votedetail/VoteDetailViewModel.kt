package com.susu.feature.community.votedetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.susu.core.model.Vote
import com.susu.core.model.exception.NotFoundLedgerException
import com.susu.core.ui.base.BaseViewModel
import com.susu.core.ui.extension.encodeToUri
import com.susu.domain.usecase.vote.DeleteVoteUseCase
import com.susu.domain.usecase.vote.GetVoteDetailUseCase
import com.susu.domain.usecase.vote.VoteUseCase
import com.susu.feature.community.navigation.CommunityRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import javax.inject.Inject

@HiltViewModel
class VoteDetailViewModel @Inject constructor(
    private val deleteVoteUseCase: DeleteVoteUseCase,
    private val getVoteDetailUseCase: GetVoteDetailUseCase,
    private val voteUseCase: VoteUseCase,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<VoteDetailState, VoteDetailSideEffect>(
    VoteDetailState(),
) {
    private val voteId = savedStateHandle.get<String>(CommunityRoute.VOTE_ID_ARGUMENT_NAME)!!.toLong()
    private var vote: Vote = Vote()
    private var initCount: Long = 0
    private var initIsVoted: Boolean = false

    fun getVoteDetail() = viewModelScope.launch {
        intent { copy(isLoading = true) }
        getVoteDetailUseCase(
            voteId,
        ).onSuccess {
            initCount = it.count
            initIsVoted = it.optionList.any { it.isVoted }
            this@VoteDetailViewModel.vote = it
            intent { copy(vote = it) }
        }.onFailure {
            postSideEffect(VoteDetailSideEffect.HandleException(it, ::getVoteDetail))
        }
        intent { copy(isLoading = false) }
    }

    fun vote(optionId: Long, isVoted: Boolean) {
        viewModelScope.launch {
            val isCancel = isVoted

            val alreadyVote = if (isCancel) {
                null
            } else {
                currentState.vote.optionList.find { it.isVoted }
            }

            if (alreadyVote != null) {
                voteUseCase(VoteUseCase.Param(id = voteId, isCancel = true, optionId = alreadyVote.id))
                    .onFailure {
                        handleVoteError(it, optionId, isVoted)
                    }
            }

            voteUseCase(VoteUseCase.Param(id = voteId, isCancel = isCancel, optionId = optionId))
                .onSuccess {
                    intent {
                        copy(
                            vote = vote.copy(
                                count = if (isCancel) vote.count - 1 else if (initIsVoted) initCount else initCount + 1,
                                optionList = vote.optionList.map {
                                    when (it.id) {
                                        optionId -> it.copy(isVoted = !it.isVoted, count = if (isCancel) it.count - 1 else it.count + 1)
                                        alreadyVote?.id -> it.copy(isVoted = false, count = it.count - 1)
                                        else -> it
                                    }
                                },
                            ),
                        )
                    }
                }
                .onFailure {
                    handleVoteError(it, optionId, isVoted)
                }
        }
    }

    private fun handleVoteError(it: Throwable, optionId: Long, isVoted: Boolean) {
        postSideEffect(
            VoteDetailSideEffect.HandleException(
                throwable = it,
                retry = {
                    vote(optionId = optionId, isVoted = isVoted)
                },
            ),
        )
    }

    fun showDeleteDialog() = postSideEffect(
        VoteDetailSideEffect.ShowDeleteDialog(
            onConfirmRequest = ::deleteVote,
        ),
    )

    private fun deleteVote() = viewModelScope.launch {
        deleteVoteUseCase(voteId)
            .onSuccess {
                postSideEffect(
                    VoteDetailSideEffect.ShowDeleteSuccessSnackbar,
                    VoteDetailSideEffect.PopBackStackWithDeleteVoteId(voteId),
                )
            }
            .onFailure { throwable ->
                postSideEffect(VoteDetailSideEffect.HandleException(throwable, ::deleteVote))
            }
    }

    fun navigateVoteEdit() = postSideEffect(VoteDetailSideEffect.NavigateVoteEdit(vote))

    fun popBackStack() = postSideEffect(VoteDetailSideEffect.PopBackStackWithToUpdateVote(Json.encodeToUri(currentState.vote)))
}
