package com.susu.feature.received.ledgeradd

import androidx.lifecycle.viewModelScope
import com.susu.core.model.Ledger
import com.susu.core.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LedgerAddViewModel @Inject constructor(
) : BaseViewModel<LedgerAddState, LedgerAddSideEffect>(
    LedgerAddState(),
) {
    fun goToPrevStep() {
        when(currentState.currentStep) {
            LedgerAddStep.CATEGORY -> postSideEffect(LedgerAddSideEffect.PopBackStack)
            LedgerAddStep.NAME -> intent { copy(currentStep = LedgerAddStep.CATEGORY) }
            LedgerAddStep.DATE -> intent { copy(currentStep = LedgerAddStep.NAME) }
        }
    }

    fun goToNextStep() {
        when(currentState.currentStep) {
            LedgerAddStep.CATEGORY -> intent { copy(currentStep = LedgerAddStep.NAME) }
            LedgerAddStep.NAME -> intent { copy(currentStep = LedgerAddStep.DATE) }
            LedgerAddStep.DATE -> { /* TODO 장부 추가 서버 연동 */ }
        }
    }

}
