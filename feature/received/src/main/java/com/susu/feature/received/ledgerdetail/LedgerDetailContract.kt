package com.susu.feature.received.ledgerdetail

import com.susu.core.ui.base.SideEffect
import com.susu.core.ui.base.UiState

data class LedgerDetailState(
    val id: Int = 0,
) : UiState

sealed interface LedgerDetailSideEffect : SideEffect
