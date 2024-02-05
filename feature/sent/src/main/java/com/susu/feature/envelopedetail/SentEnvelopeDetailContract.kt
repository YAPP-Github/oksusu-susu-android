package com.susu.feature.envelopedetail

import com.susu.core.model.EnvelopeDetail
import com.susu.core.ui.base.SideEffect
import com.susu.core.ui.base.UiState

data class SentEnvelopeDetailState(
    val isLoading: Boolean = false,
    val envelopeDetail: EnvelopeDetail = EnvelopeDetail(),
) : UiState

sealed interface SentEnvelopeDetailEffect : SideEffect {
    data class NavigateEnvelopeEdit(val envelopeDetail: EnvelopeDetail) : SentEnvelopeDetailEffect
    data object PopBackStack : SentEnvelopeDetailEffect
    data object ShowDeleteSuccessSnackBar : SentEnvelopeDetailEffect
    data object ShowDeleteDialog : SentEnvelopeDetailEffect
    data class HandleException(val throwable: Throwable, val retry: () -> Unit) : SentEnvelopeDetailEffect
}
