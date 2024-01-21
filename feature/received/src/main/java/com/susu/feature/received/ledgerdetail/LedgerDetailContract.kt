package com.susu.feature.received.ledgerdetail

import com.susu.core.model.Ledger
import com.susu.core.ui.base.SideEffect
import com.susu.core.ui.base.UiState

data class LedgerDetailState(
    val name: String = "",
    val money: Int = 0,
    val count: Int = 0,
    val category: String = "",
    val startDate: String = "",
    val endDate: String = "",
) : UiState

sealed interface LedgerDetailSideEffect : SideEffect {
    data object NavigateEnvelopeAdd : LedgerDetailSideEffect
    data object NavigateEnvelopeDetail : LedgerDetailSideEffect
    data class NavigateLedgerEdit(val ledger: Ledger) : LedgerDetailSideEffect
    data class PopBackStackWithLedger(val ledger: String) : LedgerDetailSideEffect
    data class PopBackStackWithDeleteLedgerId(val ledgerId: Int) : LedgerDetailSideEffect
    data class ShowDeleteDialog(val onConfirmRequest: () -> Unit) : LedgerDetailSideEffect
    data object ShowDeleteSuccessSnackbar : LedgerDetailSideEffect
    data class ShowSnackbar(val msg: String) : LedgerDetailSideEffect
    data class HandleException(val throwable: Throwable, val retry: () -> Unit) : LedgerDetailSideEffect
}
