package com.susu.feature.community.voteedit

import com.susu.core.model.Category
import com.susu.core.ui.base.SideEffect
import com.susu.core.ui.base.UiState
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

data class VoteEditState(
    val categoryConfigList: PersistentList<Category> = persistentListOf(),
    val selectedBoardId: Long = 0,
    val voteOptionStateList: PersistentList<String> = persistentListOf(),
    val content: String = "",
    val isLoading: Boolean = false,
) : UiState {
    val buttonEnabled = content.length in 1..50 &&
        voteOptionStateList.all { it.length in 1..10 }
}

sealed interface VoteEditSideEffect : SideEffect {
    data object ShowCanNotChangeOptionSnackbar : VoteEditSideEffect
    data object PopBackStack : VoteEditSideEffect
    data class HandleException(val throwable: Throwable, val retry: () -> Unit) : VoteEditSideEffect
}
