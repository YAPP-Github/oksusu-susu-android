package com.susu.feature.received.envelopeadd

import com.susu.core.ui.base.SideEffect
import com.susu.core.ui.base.UiState

data class ReceivedEnvelopeAddState(
    val temp: String = "",
) : UiState

sealed interface ReceivedEnvelopeAddSideEffect : SideEffect {
    data object PopBackStack : ReceivedEnvelopeAddSideEffect
    data class ShowSnackbar(val msg: String) : ReceivedEnvelopeAddSideEffect
    data class HandleException(val throwable: Throwable, val retry: () -> Unit) : ReceivedEnvelopeAddSideEffect
}
