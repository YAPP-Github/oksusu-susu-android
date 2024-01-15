package com.susu.feature.received.ledgerdetail

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

sealed interface LedgerDetailSideEffect : SideEffect
