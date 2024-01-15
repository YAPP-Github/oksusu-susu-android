package com.susu.feature.received.search

import com.susu.core.model.Ledger
import com.susu.core.ui.base.SideEffect
import com.susu.core.ui.base.UiState
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

data class LedgerSearchState(
    val searchKeyword: String = "",
    val recentSearchKeywordList: PersistentList<String> = persistentListOf(),
    val ledgerList: PersistentList<Ledger> = persistentListOf(),
    val isLoading: Boolean = false,
    val showSearchResultEmpty: Boolean = false,
) : UiState

sealed interface LedgerSearchSideEffect : SideEffect {
    data object PopBackStack : LedgerSearchSideEffect
    data class NavigateLedgerDetail(val id: Int) : LedgerSearchSideEffect
}
