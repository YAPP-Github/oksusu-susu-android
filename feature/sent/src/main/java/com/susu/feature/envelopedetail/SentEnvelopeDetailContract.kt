package com.susu.feature.envelopedetail

import com.susu.core.model.EnvelopeDetail
import com.susu.core.ui.base.SideEffect
import com.susu.core.ui.base.UiState

data class SentEnvelopeDetailState(
    val isLoading: Boolean = false,
    val envelopeDetail: EnvelopeDetail = EnvelopeDetail(),
) : UiState

sealed interface SentEnvelopeDetailEffect : SideEffect {
    data object NavigateEnvelopeEdit : SentEnvelopeDetailEffect
    data object PopBackStack : SentEnvelopeDetailEffect
}
