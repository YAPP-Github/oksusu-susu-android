package com.susu.feature.received.envelopeadd.content.money

import com.susu.core.ui.MONEY_MAX_VALUE
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

    fun addMoney(money: Int) {
        val currentMoney = currentState.money.toLongOrNull() ?: 0
        val addedMoney = currentMoney + money

        if (addedMoney > MONEY_MAX_VALUE) {
            postSideEffect(MoneySideEffect.ShowNotValidSnackbar)
            return
        }
        postSideEffect(MoneySideEffect.UpdateParentMoney(addedMoney))

        intent {
            copy(
                money = addedMoney.toString(),
            )
        }
    }

    fun showKeyboardIfMoneyEmpty() {
        if (currentState.money.isEmpty()) postSideEffect(MoneySideEffect.ShowKeyboard)
    }
}
