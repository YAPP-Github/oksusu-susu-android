package com.susu.feature.envelopeadd.content.money

import com.susu.core.ui.base.SideEffect
import com.susu.core.ui.base.UiState

data class MoneyState(
    val money: String = "",
) : UiState

sealed interface MoneyEffect : SideEffect {
    data class UpdateParentMoney(val money: Long) : MoneyEffect
    data object ShowNotValidSnackbar : MoneyEffect
}
