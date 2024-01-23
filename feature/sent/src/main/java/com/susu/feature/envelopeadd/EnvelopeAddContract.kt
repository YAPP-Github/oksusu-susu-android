package com.susu.feature.envelopeadd

import com.susu.core.ui.base.SideEffect
import com.susu.core.ui.base.UiState

sealed interface EnvelopeAddEffect : SideEffect {
    data object PopBackStack : EnvelopeAddEffect
    data object CompleteEnvelopeAdd : EnvelopeAddEffect
}

data class EnvelopeAddState(
    val isLoading: Boolean = false,
    val buttonEnabled: Boolean = false,
    val currentStep: EnvelopeAddStep = EnvelopeAddStep.MONEY,
) : UiState

enum class EnvelopeAddStep {
    MONEY,
    NAME,
    RELATIONSHIP,
    EVENT,
    DATE,
    MORE,
    VISITED,
    PRESENT,
    PHONE,
    MEMO,
}


