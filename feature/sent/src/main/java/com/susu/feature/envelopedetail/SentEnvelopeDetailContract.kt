package com.susu.feature.envelopedetail

import com.susu.core.ui.base.SideEffect
import com.susu.core.ui.base.UiState

data class SentEnvelopeDetailState(
    val isLoading: Boolean = false,
) : UiState

sealed interface SentEnvelopeDetailSideEffect : SideEffect {
    data object PopBackStack : SentEnvelopeDetailSideEffect
}
