package com.susu.feature.received.envelopeadd.content.visited

import com.susu.core.ui.base.SideEffect
import com.susu.core.ui.base.UiState
import com.susu.feature.received.ledgeradd.LedgerAddStep

data class VisitedState(
    val categoryName: String = "",
    val visited: Boolean? = null,
) : UiState

sealed interface VisitedSideEffect : SideEffect {
    data class UpdateParentVisited(val visited: Boolean?) : VisitedSideEffect
}
