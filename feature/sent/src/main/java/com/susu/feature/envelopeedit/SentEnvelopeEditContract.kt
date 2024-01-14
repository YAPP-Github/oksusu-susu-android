package com.susu.feature.envelopeedit

import com.susu.core.ui.base.SideEffect
import com.susu.core.ui.base.UiState

data class SentEnvelopeEditState(
    val isLoading: Boolean = false,
) : UiState

sealed interface SentEnvelopeEditSideEffect : SideEffect {
    data object PopBackStack : SentEnvelopeEditSideEffect
}
