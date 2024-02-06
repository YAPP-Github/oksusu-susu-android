package com.susu.feature.envelopeadd

import com.susu.core.ui.base.SideEffect
import com.susu.core.ui.base.UiState

sealed interface EnvelopeAddEffect : SideEffect {
    data object PopBackStack : EnvelopeAddEffect
    data object PopBackStackWithRefresh : EnvelopeAddEffect
    data class HandleException(val throwable: Throwable, val retry: () -> Unit) : EnvelopeAddEffect
}

data class EnvelopeAddState(
    val isLoading: Boolean = false,
    val buttonEnabled: Boolean = false,
    val currentStep: EnvelopeAddStep = EnvelopeAddStep.MONEY,
    val lastPage: Boolean = false,
) : UiState {
    val buttonResId = if (lastPage) {
        com.susu.core.ui.R.string.word_done
    } else {
        com.susu.core.ui.R.string.word_next
    }

    val progress = if (lastPage) EnvelopeAddStep.entries.size else currentStep.ordinal + 1
}

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
