package com.susu.feature.received.envelopeadd.content.money

import com.susu.core.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MoneyViewModel @Inject constructor() : BaseViewModel<MoneyState, MoneySideEffect>(
    MoneyState(),
) {
    fun updateMoney(money: String) {
        if (money.length > 10) {
            postSideEffect(MoneySideEffect.ShowNotValidSnackbar)
            return
        }

        intent {
            postSideEffect(MoneySideEffect.UpdateParentMoney(money.toLongOrNull() ?: 0))
            copy(money = money)
        }
    }

    fun addMoney(textFieldMoney: String, buttonMoney: Int) {
        val currentMoney = textFieldMoney.toLongOrNull() ?: 0
        val addedMoney = currentMoney + buttonMoney
        updateMoney(addedMoney.toString())
    }

    fun showKeyboardIfMoneyEmpty() {
        if (currentState.money.isEmpty()) postSideEffect(MoneySideEffect.ShowKeyboard)
    }
}
