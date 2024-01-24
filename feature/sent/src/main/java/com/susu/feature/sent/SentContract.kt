package com.susu.feature.sent

import com.susu.core.model.EnvelopeStatics
import com.susu.core.ui.base.SideEffect
import com.susu.core.ui.base.UiState
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

data class SentState(
    val isLoading: Boolean = false,
    val envelopesList: PersistentList<EnvelopeStatics> = persistentListOf(),
    val showEmptyEnvelopes: Boolean = false,
) : UiState

sealed interface SentSideEffect : SideEffect {
    data object NavigateEnvelopeDetail : SentSideEffect
    data object NavigateEnvelopeAdd : SentSideEffect
    data object NavigateEnvelopeSearch : SentSideEffect
}
