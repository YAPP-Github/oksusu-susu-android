package com.susu.feature.envelopedetail

import com.susu.core.ui.base.SideEffect
import com.susu.core.ui.base.UiState

data class SentEnvelopeDetailState(
    val isLoading: Boolean = false,
    val money: Int = 0,
    val event: String = "",
    val name: String = "",
    val relationship: String = "",
    val date: String = "",
    val visited: Boolean? = null,
    val gift: String = "",
    val phoneNumber: String = "",
    val memo: String = "",
) : UiState

sealed interface SentEnvelopeDetailEffect : SideEffect {
    data object NavigateEnvelopeEdit : SentEnvelopeDetailEffect
    data object PopBackStack : SentEnvelopeDetailEffect
}
