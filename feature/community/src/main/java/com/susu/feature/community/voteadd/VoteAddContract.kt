package com.susu.feature.community.voteadd

import com.susu.core.model.Category
import com.susu.core.ui.base.SideEffect
import com.susu.core.ui.base.UiState
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

data class VoteAddState(
    val categoryConfigList: PersistentList<Category> = persistentListOf(),
    val selectedCategory: Category = Category(),
    val voteOptionStateList: PersistentList<VoteOptionState> = persistentListOf(VoteOptionState(), VoteOptionState()),
    val content: String = "",
    val isLoading: Boolean = false,
) : UiState {
//    val buttonEnabled = content.isNotBlank() &&
//        voteOptionStateList.all { it.content.isNotBlank() }

    val buttonEnabled = content.isNotBlank() &&
        (voteOptionStateList.count { it.content.isNotBlank() } >= 2)
}

data class VoteOptionState(
    val content: String = "",
    val isSaved: Boolean = false,
)

sealed interface VoteAddSideEffect : SideEffect {
    data object ShowInvalidContentSnackbar : VoteAddSideEffect
    data object ShowInvalidVoteOptionSnackbar : VoteAddSideEffect
    data object PopBackStack : VoteAddSideEffect
    data class PopBackStackWithVote(val vote: String) : VoteAddSideEffect
    data class HandleException(val throwable: Throwable, val retry: () -> Unit) : VoteAddSideEffect
}
