package com.susu.feature.community.votedetail

import com.susu.core.model.Vote
import com.susu.core.ui.base.SideEffect
import com.susu.core.ui.base.UiState
import com.susu.feature.community.community.CommunitySideEffect

data class VoteDetailState(
    val vote: Vote = Vote(),
    val isLoading: Boolean = false,
) : UiState

sealed interface VoteDetailSideEffect : SideEffect {
    data class ShowSnackbar(val message: String) : VoteDetailSideEffect
    data class PopBackStackWithDeleteVoteId(val voteId: Long) : VoteDetailSideEffect
    data object PopBackStackWithNeedRefresh : VoteDetailSideEffect
    data class ShowDeleteDialog(val onConfirmRequest: () -> Unit) : VoteDetailSideEffect
    data class ShowReportDialog(val onConfirmRequest: () -> Unit, val onCheckedAction: () -> Unit) : VoteDetailSideEffect
    data object ShowDeleteSuccessSnackbar : VoteDetailSideEffect
    data class PopBackStackWithToUpdateVote(val vote: String) : VoteDetailSideEffect
    data class NavigateVoteEdit(val vote: Vote) : VoteDetailSideEffect
    data class HandleException(val throwable: Throwable, val retry: () -> Unit) : VoteDetailSideEffect
}
