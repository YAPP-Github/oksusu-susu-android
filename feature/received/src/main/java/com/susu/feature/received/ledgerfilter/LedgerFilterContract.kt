package com.susu.feature.received.ledgerfilter

import com.susu.core.model.Category
import com.susu.core.ui.base.SideEffect
import com.susu.core.ui.base.UiState
import com.susu.feature.received.navigation.argument.LedgerFilterArgument
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.datetime.toKotlinLocalDateTime
import java.time.LocalDateTime

data class LedgerFilterState(
    val categoryConfig: PersistentList<Category> = persistentListOf(),
    val selectedCategoryList: PersistentList<Category> = persistentListOf(),
    val startAt: LocalDateTime? = null,
    val endAt: LocalDateTime? = null,
    val showStartDateBottomSheet: Boolean = false,
    val showEndDateBottomSheet: Boolean = false,
) : UiState

internal fun LedgerFilterState.toFilterArgument() = LedgerFilterArgument(
    selectedCategoryList = selectedCategoryList,
    startAt = startAt?.toKotlinLocalDateTime(),
    endAt = endAt?.toKotlinLocalDateTime(),
)

sealed interface LedgerFilterSideEffect : SideEffect {
    data object PopBackStack : LedgerFilterSideEffect
    data class PopBackStackWithFilter(val filter: String) : LedgerFilterSideEffect
    data class HandleException(val throwable: Throwable, val retry: () -> Unit) : LedgerFilterSideEffect
    data object LogApplyClickEvent : LedgerFilterSideEffect
}
