package com.susu.feature.received.envelopeadd.content.phone

import com.susu.core.ui.base.SideEffect
import com.susu.core.ui.base.UiState
import com.susu.feature.received.envelopeadd.content.present.PresentSideEffect

data class PhoneState(
    val phone: String = "",
    val name: String = "",
) : UiState

sealed interface PhoneSideEffect : SideEffect {
    data class UpdateParentPhone(val phone: String?) : PhoneSideEffect
    data object ShowKeyboard: PhoneSideEffect
}
