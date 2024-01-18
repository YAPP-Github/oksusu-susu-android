package com.susu.feature.received.ledgeradd

import com.susu.core.model.Ledger
import com.susu.core.ui.base.SideEffect
import com.susu.core.ui.base.UiState
import com.susu.feature.received.ledgerdetail.LedgerDetailSideEffect
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

data class LedgerAddState(
    val currentStep: LedgerAddStep = LedgerAddStep.CATEGORY,
    val buttonEnabled: Boolean = false,
    val isLoading: Boolean = false,
) : UiState

enum class LedgerAddStep {
    CATEGORY,
    NAME,
    DATE,
}

sealed interface LedgerAddSideEffect : SideEffect {
    data object PopBackStack : LedgerAddSideEffect
    data class PopBackStackWithLedger(val ledger: String) : LedgerAddSideEffect
    data class HandleException(val throwable: Throwable, val retry: () -> Unit) : LedgerAddSideEffect

}
