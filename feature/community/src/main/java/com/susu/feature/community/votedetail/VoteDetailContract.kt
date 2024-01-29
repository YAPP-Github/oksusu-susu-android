package com.susu.feature.community.votedetail

import com.susu.core.model.Vote
import com.susu.core.ui.base.SideEffect
import com.susu.core.ui.base.UiState

data class VoteDetailState(
    val vote: Vote = Vote(),
    val isLoading: Boolean = false,
) : UiState

sealed interface VoteDetailSideEffect : SideEffect {
    data class PopBackStackWithToUpdateVote(val vote: String) : VoteDetailSideEffect
    data class PopBackStackWithVote(val vote: String) : VoteDetailSideEffect
    data class HandleException(val throwable: Throwable, val retry: () -> Unit) : VoteDetailSideEffect
}
