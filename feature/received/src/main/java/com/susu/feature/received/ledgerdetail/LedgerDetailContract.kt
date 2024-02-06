package com.susu.feature.received.ledgerdetail

import androidx.annotation.StringRes
import com.susu.core.model.Envelope
import com.susu.core.model.Friend
import com.susu.core.model.Ledger
import com.susu.core.model.SearchEnvelope
import com.susu.core.ui.R
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
    val selectedFriendList: PersistentList<Friend> = persistentListOf(),
    val fromAmount: Long? = null,
    val toAmount: Long? = null,
    val showAlignBottomSheet: Boolean = false,
    val selectedAlignPosition: Int = EnvelopeAlign.RECENT.ordinal,
) : UiState {
    val isFiltered = fromAmount != null || toAmount != null || selectedFriendList.isNotEmpty()
}

enum class EnvelopeAlign(
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

sealed interface LedgerDetailSideEffect : SideEffect {
    data class NavigateEnvelopeAdd(val ledger: Ledger) : LedgerDetailSideEffect
    data class NavigateEnvelopeDetail(val envelope: Envelope, val ledger: Ledger) : LedgerDetailSideEffect
    data class NavigateLedgerEdit(val ledger: Ledger) : LedgerDetailSideEffect
    data class PopBackStackWithLedger(val ledger: String) : LedgerDetailSideEffect
    data class PopBackStackWithDeleteLedgerId(val ledgerId: Long) : LedgerDetailSideEffect
    data class ShowDeleteDialog(val onConfirmRequest: () -> Unit) : LedgerDetailSideEffect
    data object ShowDeleteSuccessSnackbar : LedgerDetailSideEffect
    data class ShowSnackbar(val msg: String) : LedgerDetailSideEffect
    data class HandleException(val throwable: Throwable, val retry: () -> Unit) : LedgerDetailSideEffect
    data class NavigateEnvelopeFilter(val filter: String) : LedgerDetailSideEffect
}
