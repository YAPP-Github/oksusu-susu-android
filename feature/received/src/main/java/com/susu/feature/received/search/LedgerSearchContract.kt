package com.susu.feature.received.search

import com.susu.core.ui.base.SideEffect
import com.susu.core.ui.base.UiState
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

data class LedgerSearchState(
    val search: String = "",
    val searchList: PersistentList<String> = persistentListOf(),
    val isLoading: Boolean = false,
) : UiState

sealed interface LedgerSearchSideEffect : SideEffect {
    data object PopBackStack : LedgerSearchSideEffect
}
