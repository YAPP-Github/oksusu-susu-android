package com.susu.feature.received.envelopedetail

import com.susu.core.model.Envelope
import com.susu.core.ui.base.SideEffect
import com.susu.core.ui.base.UiState

data class ReceivedEnvelopeDetailState(
    val money: Long = 0,
) : UiState

sealed interface ReceivedEnvelopeDetailSideEffect : SideEffect {
    data class NavigateReceivedEnvelopeEdit(val envelope: Envelope) : ReceivedEnvelopeDetailSideEffect
    data class PopBackStackWithReceivedEnvelope(val envelope: String) : ReceivedEnvelopeDetailSideEffect
    data class PopBackStackWithDeleteReceivedEnvelopeId(val envelopeId: Long) : ReceivedEnvelopeDetailSideEffect
    data class ShowDeleteDialog(val onConfirmRequest: () -> Unit) : ReceivedEnvelopeDetailSideEffect
    data object ShowDeleteSuccessSnackbar : ReceivedEnvelopeDetailSideEffect
    data class ShowSnackbar(val msg: String) : ReceivedEnvelopeDetailSideEffect
    data class HandleException(val throwable: Throwable, val retry: () -> Unit) : ReceivedEnvelopeDetailSideEffect
}
