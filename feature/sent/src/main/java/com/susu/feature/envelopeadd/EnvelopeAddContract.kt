package com.susu.feature.envelopeadd

import com.susu.core.ui.base.SideEffect
import com.susu.core.ui.base.UiState

sealed interface EnvelopeAddEffect : SideEffect {
    data object PopBackStack : EnvelopeAddEffect
    data object PopBackStackWithRefresh : EnvelopeAddEffect
    data class HandleException(val throwable: Throwable, val retry: () -> Unit) : EnvelopeAddEffect
    data class LogClickNextButton(val step: EnvelopeAddStep) : EnvelopeAddEffect
    data class LogClickBackButton(val step: EnvelopeAddStep) : EnvelopeAddEffect
}

/**
 * @param fromEnvelope 보내요 봉투 화면에서 추가 화면으로 진입할 시 true. MORE STEP에서 전화번호를 감추기 위함.
 * */
data class EnvelopeAddState(
    val isLoading: Boolean = false,
    val buttonEnabled: Boolean = false,
    val currentStep: EnvelopeAddStep = EnvelopeAddStep.MONEY,
    val lastPage: Boolean = false,
    val friendName: String = "",
    val fromEnvelope: Boolean = false,
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
