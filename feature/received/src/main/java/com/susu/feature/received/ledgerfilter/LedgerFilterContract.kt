package com.susu.feature.received.ledgerfilter

import com.susu.core.model.Category
import com.susu.core.ui.base.SideEffect
import com.susu.core.ui.base.UiState
import com.susu.feature.received.navigation.argument.FilterArgument
import kotlinx.datetime.toKotlinLocalDateTime
import java.time.LocalDateTime

data class LedgerFilterState(
    val selectedCategoryList: List<Category> = emptyList(),
    val startAt: LocalDateTime? = null,
    val endAt: LocalDateTime? = null,
) : UiState

internal fun LedgerFilterState.toFilterArgument() = FilterArgument(
    selectedCategoryList = selectedCategoryList,
    startAt = startAt?.toKotlinLocalDateTime(),
    endAt = endAt?.toKotlinLocalDateTime(),
)

sealed interface LedgerFilterSideEffect : SideEffect {
    data object PopBackStack : LedgerFilterSideEffect
    data class PopBackStackWithLedger(val ledger: String) : LedgerFilterSideEffect
    data class HandleException(val throwable: Throwable, val retry: () -> Unit) : LedgerFilterSideEffect
}
