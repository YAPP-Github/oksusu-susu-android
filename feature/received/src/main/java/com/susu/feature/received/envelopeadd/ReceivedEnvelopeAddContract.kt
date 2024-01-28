package com.susu.feature.received.envelopeadd

import androidx.annotation.StringRes
import com.susu.core.ui.base.SideEffect
import com.susu.core.ui.base.UiState
import com.susu.feature.received.R

data class ReceivedEnvelopeAddState(
    val currentStep: EnvelopeAddStep = EnvelopeAddStep.MONEY,
    val buttonEnabled: Boolean = false,
    val lastPage: Boolean = false,
    val isLoading: Boolean = false,
) : UiState {
    val buttonResId = if (lastPage) {
        com.susu.core.ui.R.string.word_done
    } else {
        com.susu.core.ui.R.string.word_next
    }

    val progress = if (lastPage) EnvelopeAddStep.entries.size else currentStep.ordinal + 1
}

enum class EnvelopeAddStep(
    @StringRes val stringResId: Int? = null,
) {
    MONEY,
    NAME,
    RELATIONSHIP,
    DATE,
    MORE,
    VISITED(R.string.envelop_add_step_visited),
    PRESENT(R.string.envelop_add_step_present),
    PHONE(R.string.envelop_add_step_phone),
    MEMO(R.string.envelop_add_step_memo),
}

sealed interface ReceivedEnvelopeAddSideEffect : SideEffect {
    data object PopBackStack : ReceivedEnvelopeAddSideEffect
    data class HandleException(val throwable: Throwable, val retry: () -> Unit) : ReceivedEnvelopeAddSideEffect
}
