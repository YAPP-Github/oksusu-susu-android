package com.susu.feature.sent

import com.susu.core.model.FriendStatistics
import com.susu.core.ui.base.SideEffect
import com.susu.core.ui.base.UiState
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

data class SentState(
    val isLoading: Boolean = false,
    val envelopesList: PersistentList<FriendStatistics> = persistentListOf(),
    val showEmptyEnvelopes: Boolean = false,
) : UiState

sealed interface SentEffect : SideEffect {
    data object NavigateEnvelope : SentEffect
    data object NavigateEnvelopeAdd : SentEffect
}
