package com.susu.feature.received.envelopeadd

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.susu.core.model.Category
import com.susu.core.model.Ledger
import com.susu.core.model.exception.NotFoundLedgerException
import com.susu.core.ui.base.BaseViewModel
import com.susu.core.ui.extension.decodeFromUri
import com.susu.core.ui.extension.encodeToUri
import com.susu.core.ui.util.to_yyyy_dot_MM_dot_dd
import com.susu.domain.usecase.ledger.DeleteLedgerUseCase
import com.susu.feature.received.ledgeradd.LedgerAddSideEffect
import com.susu.feature.received.ledgeradd.LedgerAddStep
import com.susu.feature.received.navigation.ReceivedRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toKotlinLocalDateTime
import kotlinx.serialization.json.Json
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class ReceivedEnvelopeAddViewModel @Inject constructor(
) : BaseViewModel<ReceivedEnvelopeAddState, ReceivedEnvelopeAddSideEffect>(
    ReceivedEnvelopeAddState(),
) {
    private var money: Long = 0
    private var name: String = ""

    fun goToPrevStep() = intent {
        val prevStep = when (currentState.currentStep) {
            EnvelopeAddStep.MONEY -> {
                postSideEffect(ReceivedEnvelopeAddSideEffect.PopBackStack)
                EnvelopeAddStep.MONEY
            }

            EnvelopeAddStep.NAME -> EnvelopeAddStep.MONEY
            EnvelopeAddStep.RELATIONSHIP -> EnvelopeAddStep.NAME
            EnvelopeAddStep.MORE -> EnvelopeAddStep.RELATIONSHIP
            EnvelopeAddStep.VISITED -> EnvelopeAddStep.MORE
            EnvelopeAddStep.PRESENT -> EnvelopeAddStep.VISITED
            EnvelopeAddStep.PHONE -> EnvelopeAddStep.PRESENT
            EnvelopeAddStep.MEMO -> EnvelopeAddStep.PHONE
        }

        copy(currentStep = prevStep)
    }

    fun goToNextStep() = intent {
        val nextStep = when (currentState.currentStep) {
            EnvelopeAddStep.MONEY -> EnvelopeAddStep.NAME
            EnvelopeAddStep.NAME -> EnvelopeAddStep.RELATIONSHIP
            EnvelopeAddStep.RELATIONSHIP -> EnvelopeAddStep.MORE
            EnvelopeAddStep.MORE -> EnvelopeAddStep.VISITED
            EnvelopeAddStep.VISITED -> EnvelopeAddStep.PRESENT
            EnvelopeAddStep.PRESENT -> EnvelopeAddStep.PHONE
            EnvelopeAddStep.PHONE -> EnvelopeAddStep.MEMO
            EnvelopeAddStep.MEMO -> {
                /* 봉투 생성 */
                EnvelopeAddStep.MEMO
            }
        }

        copy(currentStep = nextStep)
    }

    fun updateMoney(money: Long) = intent {
        this@ReceivedEnvelopeAddViewModel.money = money
        copy(
            buttonEnabled = money > 0,
        )
    }

    fun updateName(name: String) = intent {
        this@ReceivedEnvelopeAddViewModel.name = name
        copy(
            buttonEnabled = name.isNotEmpty(),
        )
    }
}
