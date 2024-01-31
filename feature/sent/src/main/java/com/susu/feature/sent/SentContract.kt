package com.susu.feature.sent

import com.susu.core.model.EnvelopeSearch
import com.susu.core.model.Friend
import com.susu.core.model.FriendStatistics
import com.susu.core.ui.base.SideEffect
import com.susu.core.ui.base.UiState
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

data class SentState(
    val isLoading: Boolean = false,
    val envelopesList: PersistentList<FriendStatisticsState> = persistentListOf(),
    val showEmptyEnvelopes: Boolean = false,
) : UiState

data class FriendStatisticsState(
    val friend: Friend = Friend(),
    val receivedAmounts: Int = 0,
    val sentAmounts: Int = 0,
    val totalAmounts: Int = 0,
    val envelopesHistoryList: PersistentList<EnvelopeSearch> = persistentListOf(),
    val expand: Boolean = false,
)

internal fun FriendStatistics.toState() = FriendStatisticsState(
    friend = friend,
    receivedAmounts = receivedAmounts,
    sentAmounts = sentAmounts,
    totalAmounts = totalAmounts,
)

sealed interface SentEffect : SideEffect {
    data object NavigateEnvelope : SentEffect
    data object NavigateEnvelopeAdd : SentEffect
}
