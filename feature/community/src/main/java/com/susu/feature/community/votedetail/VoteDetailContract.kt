package com.susu.feature.community.votedetail

import com.susu.core.model.Vote
import com.susu.core.ui.base.SideEffect
import com.susu.core.ui.base.UiState

data class VoteDetailState(
    val vote: Vote = Vote(),
    val isLoading: Boolean = false,
) : UiState

sealed interface VoteDetailSideEffect : SideEffect {
    data class PopBackStackWithDeleteVoteId(val voteId: Long) : VoteDetailSideEffect
    data class ShowDeleteDialog(val onConfirmRequest: () -> Unit) : VoteDetailSideEffect
    data object ShowDeleteSuccessSnackbar : VoteDetailSideEffect
    data class PopBackStackWithToUpdateVote(val vote: String) : VoteDetailSideEffect
    data class NavigateVoteEdit(val vote: Vote) : VoteDetailSideEffect
    data class HandleException(val throwable: Throwable, val retry: () -> Unit) : VoteDetailSideEffect
}
