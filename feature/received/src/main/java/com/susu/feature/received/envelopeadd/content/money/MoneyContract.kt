package com.susu.feature.received.envelopeadd.content.money

import com.susu.core.ui.base.SideEffect
import com.susu.core.ui.base.UiState

data class MoneyState(
    val money: String = "",
) : UiState

sealed interface MoneySideEffect : SideEffect {
    data class UpdateParentMoney(val money: Long) : MoneySideEffect
}
