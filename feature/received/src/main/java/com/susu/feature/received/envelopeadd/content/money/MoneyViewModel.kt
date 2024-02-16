package com.susu.feature.received.envelopeadd.content.money

import com.susu.core.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MoneyViewModel @Inject constructor() : BaseViewModel<MoneyState, MoneySideEffect>(
    MoneyState(),
) {
    fun updateMoney(money: String) = intent {
        postSideEffect(MoneySideEffect.UpdateParentMoney(money.toLongOrNull() ?: 0))
        copy(money = money)
    }

    fun addMoney(money: Int) = intent {
        val currentMoney = this.money.toLongOrNull() ?: 0
        val addedMoney = money + currentMoney
        postSideEffect(MoneySideEffect.UpdateParentMoney(addedMoney))
        copy(
            money = addedMoney.toString(),
        )
    }

    fun showKeyboardIfMoneyEmpty() {
        if (currentState.money.isEmpty()) postSideEffect(MoneySideEffect.ShowKeyboard)
    }
}
