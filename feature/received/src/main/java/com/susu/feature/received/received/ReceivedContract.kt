package com.susu.feature.received.received

import androidx.annotation.StringRes
import com.susu.core.model.Category
import com.susu.core.model.Ledger
import com.susu.core.ui.R
import com.susu.core.ui.base.SideEffect
import com.susu.core.ui.base.UiState
import com.susu.feature.received.navigation.argument.LedgerFilterArgument
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import java.time.LocalDateTime

data class ReceivedState(
    val isLoading: Boolean = false,
    val showAlignBottomSheet: Boolean = false,
    val selectedAlignPosition: Int = LedgerAlign.RECENT.ordinal,
    val ledgerList: PersistentList<Ledger> = persistentListOf(),
    val showEmptyLedger: Boolean = false,
    val startAt: LocalDateTime? = null,
    val endAt: LocalDateTime? = null,
    val selectedCategoryList: PersistentList<Category> = persistentListOf(),
) : UiState {
    val isFiltered = startAt != null || endAt != null || selectedCategoryList.isNotEmpty()
}

enum class LedgerAlign(
    @StringRes val stringResId: Int,
    val query: String,
) {
    RECENT(
        stringResId = R.string.word_align_recently,
        query = "createdAt,desc",
    ),
    OUTDATED(
        stringResId = R.string.word_align_outdated,
        query = "createdAt,asc",
    ),
    HIGH_AMOUNT(
        stringResId = R.string.word_align_high_amount,
        query = "totalReceivedAmounts,desc",
    ),
    LOW_AMOUNT(
        stringResId = R.string.word_align_low_amount,
        query = "totalReceivedAmounts,asc",
    ),
}

sealed interface ReceivedEffect : SideEffect {
    data class NavigateLedgerDetail(val ledger: Ledger) : ReceivedEffect
    data object NavigateLedgerAdd : ReceivedEffect
    data object NavigateLedgerSearch : ReceivedEffect
    data class NavigateLedgerFilter(val filter: LedgerFilterArgument) : ReceivedEffect
    data object ScrollToTop : ReceivedEffect
    data object LogSearchIconClickEvent : ReceivedEffect
}
