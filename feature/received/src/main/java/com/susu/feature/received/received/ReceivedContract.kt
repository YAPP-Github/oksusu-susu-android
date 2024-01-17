package com.susu.feature.received.received

import com.susu.core.model.Ledger
import com.susu.core.ui.base.SideEffect
import com.susu.core.ui.base.UiState
import com.susu.feature.received.navigation.argument.FilterArgument
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

data class ReceivedState(
    val isLoading: Boolean = false,
    val showAlignBottomSheet: Boolean = false,
    val ledgerList: PersistentList<Ledger> = persistentListOf(),
    val showEmptyLedger: Boolean = false,
) : UiState

sealed interface ReceivedEffect : SideEffect {
    data class NavigateLedgerDetail(val ledger: Ledger) : ReceivedEffect
    data object NavigateLedgerAdd : ReceivedEffect
    data object NavigateLedgerSearch : ReceivedEffect
    data class NavigateLedgerFilter(val filter: FilterArgument) : ReceivedEffect
}
