package com.susu.feature.envelopeedit

import com.susu.core.ui.base.SideEffect
import com.susu.core.ui.base.UiState

data class SentEnvelopeEditState(
    val isLoading: Boolean = false,
) : UiState

sealed interface SentEnvelopeEditEffect : SideEffect {
//    data class NavigateEnvelope(val id: Long) : SentEnvelopeEditEffect
    data object PopBackStack : SentEnvelopeEditEffect
}
