package com.susu.feature.received.envelopeadd.content.present

import com.susu.core.ui.base.SideEffect
import com.susu.core.ui.base.UiState

data class PresentState(
    val present: String = "",
) : UiState

sealed interface PresentSideEffect : SideEffect {
    data class UpdateParentPresent(val present: String?) : PresentSideEffect
    data object ShowKeyboard : PresentSideEffect
    data object ShowNotValidSnackbar : PresentSideEffect
}
