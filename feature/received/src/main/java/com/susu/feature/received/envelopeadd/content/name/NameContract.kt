package com.susu.feature.received.envelopeadd.content.name

import com.susu.core.ui.base.SideEffect
import com.susu.core.ui.base.UiState
import com.susu.feature.received.ledgeradd.LedgerAddStep

data class NameState(
    val name: String = "",
) : UiState

sealed interface NameSideEffect : SideEffect {
    data class UpdateParentName(val name: String) : NameSideEffect
}
