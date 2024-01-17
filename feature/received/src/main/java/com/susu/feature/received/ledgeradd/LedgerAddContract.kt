package com.susu.feature.received.ledgeradd

import com.susu.core.model.Ledger
import com.susu.core.ui.base.SideEffect
import com.susu.core.ui.base.UiState
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

data class LedgerAddState(
    val currentStep: LedgerAddStep = LedgerAddStep.CATEGORY,
    val buttonEnabled: Boolean = false,
) : UiState

enum class LedgerAddStep {
    CATEGORY,
    NAME,
    DATE,
}

sealed interface LedgerAddSideEffect : SideEffect {
    data object PopBackStack : LedgerAddSideEffect
}
