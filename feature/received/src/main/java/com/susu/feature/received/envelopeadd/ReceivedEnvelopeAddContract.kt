package com.susu.feature.received.envelopeadd

import com.susu.core.ui.base.SideEffect
import com.susu.core.ui.base.UiState
import com.susu.feature.received.ledgeradd.LedgerAddStep

data class ReceivedEnvelopeAddState(
    val currentStep: EnvelopeAddStep = EnvelopeAddStep.MONEY,
    val buttonEnabled: Boolean = false,
    val isLoading: Boolean = false,
) : UiState

enum class EnvelopeAddStep {
    MONEY,
    NAME,
    RELATIONSHIP,
    MORE,
    VISITED,
    PRESENT,
    PHONE,
    MEMO,
}

sealed interface ReceivedEnvelopeAddSideEffect : SideEffect {
    data object PopBackStack : ReceivedEnvelopeAddSideEffect
    data class HandleException(val throwable: Throwable, val retry: () -> Unit) : ReceivedEnvelopeAddSideEffect
}
