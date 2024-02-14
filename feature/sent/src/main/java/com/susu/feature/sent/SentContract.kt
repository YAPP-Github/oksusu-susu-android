package com.susu.feature.sent

import androidx.annotation.StringRes
import com.susu.core.model.EnvelopeSearch
import com.susu.core.model.Friend
import com.susu.core.model.FriendStatistics
import com.susu.core.ui.R
import com.susu.core.ui.base.SideEffect
import com.susu.core.ui.base.UiState
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

data class SentState(
    val isLoading: Boolean = false,
    val envelopesList: PersistentList<FriendStatisticsState> = persistentListOf(),
    val showEmptyEnvelopes: Boolean = false,
    val selectedFriendList: PersistentList<Friend> = persistentListOf(),
    val fromAmount: Long? = null,
    val toAmount: Long? = null,
    val selectedAlignPosition: Int = EnvelopeAlign.RECENT.ordinal,
    val showAlignBottomSheet: Boolean = false,
) : UiState {
    val isFiltered = fromAmount != null || toAmount != null || selectedFriendList.isNotEmpty()
}

data class FriendStatisticsState(
    val friend: Friend = Friend(),
    val receivedAmounts: Int = 0,
    val sentAmounts: Int = 0,
    val totalAmounts: Int = 0,
    val envelopesHistoryList: PersistentList<EnvelopeSearch> = persistentListOf(),
    val expand: Boolean = false,
)

internal fun FriendStatistics.toState(expand: Boolean = false) = FriendStatisticsState(
    friend = friend,
    receivedAmounts = receivedAmounts,
    sentAmounts = sentAmounts,
    totalAmounts = totalAmounts,
    expand = expand,
)

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
        query = "amount,desc",
    ),
    LOW_AMOUNT(
        stringResId = R.string.word_align_low_amount,
        query = "amount,asc",
    ),
}

sealed interface SentEffect : SideEffect {
    data class NavigateEnvelope(val id: Long) : SentEffect
    data object NavigateEnvelopeAdd : SentEffect
    data object NavigateEnvelopeSearch : SentEffect
    data class NavigateEnvelopeFilter(val filter: String) : SentEffect
    data object ScrollToTop : SentEffect
    data class FocusToLastEnvelope(val lastIndex: Int) : SentEffect
}
