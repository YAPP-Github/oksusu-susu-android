package com.susu.feature.received.ledgeradd.content.name

import com.susu.core.ui.base.SideEffect
import com.susu.core.ui.base.UiState

data class NameState(
    val name: String = "",
) : UiState

sealed interface NameSideEffect : SideEffect {
    data class UpdateParentName(val name: String) : NameSideEffect
}
