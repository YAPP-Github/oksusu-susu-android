package com.susu.feature.received.envelopeadd.content.present

import com.susu.core.ui.base.SideEffect
import com.susu.core.ui.base.UiState
import com.susu.feature.received.ledgeradd.LedgerAddStep

data class PresentState(
    val present: String = "",
) : UiState

sealed interface PresentSideEffect : SideEffect {
    data class UpdateParentPresent(val present: String?) : PresentSideEffect
}
