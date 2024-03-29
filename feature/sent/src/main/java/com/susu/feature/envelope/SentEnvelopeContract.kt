package com.susu.feature.envelope

import com.susu.core.model.EnvelopeSearch
import com.susu.core.model.Friend
import com.susu.core.model.FriendStatistics
import com.susu.core.ui.base.SideEffect
import com.susu.core.ui.base.UiState
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

data class SentEnvelopeState(
    val isLoading: Boolean = false,
    val envelopeInfo: FriendStatistics = FriendStatistics(),
    val envelopeHistoryList: PersistentList<EnvelopeSearch> = persistentListOf(),
) : UiState

sealed interface SentEnvelopeSideEffect : SideEffect {
    data class NavigateEnvelopeDetail(val id: Long) : SentEnvelopeSideEffect
    data class NavigateEnvelopeAdd(val friend: Friend) : SentEnvelopeSideEffect
    data object PopBackStack : SentEnvelopeSideEffect
    data class PopBackStackWithDeleteFriendId(val id: Long) : SentEnvelopeSideEffect
}
