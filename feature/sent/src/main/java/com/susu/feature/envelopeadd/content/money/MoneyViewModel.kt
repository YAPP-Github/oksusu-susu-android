package com.susu.feature.envelopeadd.content.money

import androidx.lifecycle.viewModelScope
import com.susu.core.ui.MONEY_MAX_VALUE
import com.susu.core.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoneyViewModel @Inject constructor() : BaseViewModel<MoneyState, MoneyEffect>(MoneyState()) {

    fun updateMoney(money: String) {
        if (money.length > 10) {
            postSideEffect(MoneyEffect.ShowNotValidSnackbar)
            return
        }

        intent {
            postSideEffect(MoneyEffect.UpdateParentMoney(money.toLongOrNull() ?: 0))
            copy(money = money)
        }
    }

    fun addMoney(money: Int) {
        val currentMoney = currentState.money.toLongOrNull() ?: 0
        val addedMoney = money + currentMoney
        if (addedMoney > MONEY_MAX_VALUE) {
            postSideEffect(MoneyEffect.ShowNotValidSnackbar)
            return
        }
        postSideEffect(MoneyEffect.UpdateParentMoney(addedMoney))

        intent {
            copy(
                money = addedMoney.toString(),
            )
        }
    }

    fun showKeyboardIfTextEmpty() {
        if (currentState.money.isEmpty()) {
            postSideEffect(MoneyEffect.ShowKeyboard)
        }
    }
}
