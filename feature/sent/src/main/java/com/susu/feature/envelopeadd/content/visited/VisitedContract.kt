package com.susu.feature.envelopeadd.content.visited

import com.susu.core.ui.base.SideEffect
import com.susu.core.ui.base.UiState

data class VisitedState(
    val categoryName: String = "",
    val visited: Boolean? = null,
) : UiState

sealed interface VisitedSideEffect : SideEffect {
    data class UpdateParentVisited(val visited: Boolean?) : VisitedSideEffect
}
