package com.susu.feature.received.ledgerdetail

import com.susu.core.model.Ledger
import com.susu.core.model.SearchEnvelope
import com.susu.core.ui.base.SideEffect
import com.susu.core.ui.base.UiState
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

data class LedgerDetailState(
    val name: String = "",
    val money: Int = 0,
    val count: Int = 0,
    val category: String = "",
    val startDate: String = "",
    val endDate: String = "",
    val envelopeList: PersistentList<SearchEnvelope> = persistentListOf(),
) : UiState

sealed interface LedgerDetailSideEffect : SideEffect {
    data class NavigateEnvelopeAdd(val categoryName: String, val ledgerId: Long) : LedgerDetailSideEffect
    data object NavigateEnvelopeDetail : LedgerDetailSideEffect
    data class NavigateLedgerEdit(val ledger: Ledger) : LedgerDetailSideEffect
    data class PopBackStackWithLedger(val ledger: String) : LedgerDetailSideEffect
    data class PopBackStackWithDeleteLedgerId(val ledgerId: Long) : LedgerDetailSideEffect
    data class ShowDeleteDialog(val onConfirmRequest: () -> Unit) : LedgerDetailSideEffect
    data object ShowDeleteSuccessSnackbar : LedgerDetailSideEffect
    data class ShowSnackbar(val msg: String) : LedgerDetailSideEffect
    data class HandleException(val throwable: Throwable, val retry: () -> Unit) : LedgerDetailSideEffect
}
