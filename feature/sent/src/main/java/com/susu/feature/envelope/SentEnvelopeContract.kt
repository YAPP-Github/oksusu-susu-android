package com.susu.feature.envelope

import com.susu.core.ui.base.SideEffect
import com.susu.core.ui.base.UiState

data class SentEnvelopeState(
    val isLoading: Boolean = false,
) : UiState

sealed interface SentEnvelopeSideEffect : SideEffect {
    data object PopBackStack : SentEnvelopeSideEffect
}
