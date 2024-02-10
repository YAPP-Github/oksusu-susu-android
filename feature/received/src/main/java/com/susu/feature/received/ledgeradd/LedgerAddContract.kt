package com.susu.feature.received.ledgeradd

import com.susu.core.ui.base.SideEffect
import com.susu.core.ui.base.UiState
import com.susu.feature.received.ledgeradd.content.date.DateSideEffect

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
    data object HideKeyboard: LedgerAddSideEffect
    data object PopBackStack : LedgerAddSideEffect
    data class PopBackStackWithLedger(val ledger: String) : LedgerAddSideEffect
    data class HandleException(val throwable: Throwable, val retry: () -> Unit) : LedgerAddSideEffect
}
