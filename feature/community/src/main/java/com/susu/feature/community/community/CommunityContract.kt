package com.susu.feature.community.community

import com.susu.core.model.Category
import com.susu.core.model.Vote
import com.susu.core.ui.base.SideEffect
import com.susu.core.ui.base.UiState
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

data class CommunityState(
    val categoryConfigList: PersistentList<Category> = persistentListOf(),
    val selectedCategory: Category? = null,
    val popularVoteList: PersistentList<Vote> = persistentListOf(),
    val voteList: PersistentList<Vote> = persistentListOf(),
    val isCheckedVotePopular: Boolean = false,
    val isCheckShowMine: Boolean = false,
    val isLoading: Boolean = false,
) : UiState

sealed interface CommunitySideEffect : SideEffect {
    data class HandleException(val throwable: Throwable, val retry: () -> Unit) : CommunitySideEffect
    data class ShowSnackbar(val message: String) : CommunitySideEffect
    data object NavigateVoteAdd : CommunitySideEffect
    data object NavigateVoteSearch : CommunitySideEffect
    data class NavigateVoteDetail(val voteId: Long) : CommunitySideEffect
    data class ShowReportDialog(val onConfirmRequest: () -> Unit, val onCheckedAction: () -> Unit) : CommunitySideEffect
    data object LogSearchIconClickEvent : CommunitySideEffect
}
