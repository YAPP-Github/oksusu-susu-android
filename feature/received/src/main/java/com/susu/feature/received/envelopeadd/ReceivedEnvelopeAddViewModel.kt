package com.susu.feature.received.envelopeadd

import com.susu.core.model.RelationShip
import com.susu.core.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ReceivedEnvelopeAddViewModel @Inject constructor(
) : BaseViewModel<ReceivedEnvelopeAddState, ReceivedEnvelopeAddSideEffect>(
    ReceivedEnvelopeAddState(),
) {
    private var money: Long = 0
    private var name: String = ""
    private var relationShip: RelationShip? = null
    private var moreStep: List<EnvelopeAddStep> = emptyList()

    fun goToPrevStep() = intent {
        val prevStep = when (currentStep) {
            EnvelopeAddStep.MONEY -> {
                postSideEffect(ReceivedEnvelopeAddSideEffect.PopBackStack)
                EnvelopeAddStep.MONEY
            }

            EnvelopeAddStep.NAME -> EnvelopeAddStep.MONEY
            EnvelopeAddStep.RELATIONSHIP -> EnvelopeAddStep.NAME
            EnvelopeAddStep.MORE -> EnvelopeAddStep.RELATIONSHIP
            else -> goToPrevStepInMore(currentStep)
        }

        copy(
            currentStep = prevStep,
            lastPage = false,
        )
    }

    private fun goToPrevStepInMore(currentStep: EnvelopeAddStep): EnvelopeAddStep {
        if (moreStep.isEmpty()) {
            return EnvelopeAddStep.MORE
        }

        val prevStepIndex = run {
            val currentStepIndex = moreStep.indexOf(currentStep)
            if (currentStepIndex == -1) EnvelopeAddStep.MORE.ordinal else currentStepIndex - 1
        }

        return moreStep.getOrNull(prevStepIndex) ?: EnvelopeAddStep.MORE
    }

    fun goToNextStep() = intent {
        val nextStep = when (currentStep) {
            EnvelopeAddStep.MONEY -> EnvelopeAddStep.NAME
            EnvelopeAddStep.NAME -> EnvelopeAddStep.RELATIONSHIP
            EnvelopeAddStep.RELATIONSHIP -> EnvelopeAddStep.MORE
            else -> goToNextStepInMore(currentStep)
        }

        if (nextStep == null) {
            // TODO 봉투 생성
            copy()
        } else {
            copy(
                currentStep = nextStep,
                lastPage = nextStep == moreStep.lastOrNull(),
            )
        }
    }

    private fun goToNextStepInMore(currentStep: EnvelopeAddStep): EnvelopeAddStep? {
        if (moreStep.isEmpty() || currentState.lastPage) {
            return null
        }

        val nextStepIndex = when (currentStep) {
            EnvelopeAddStep.MORE -> 0
            else -> {
                val currentStepIndex = moreStep.indexOf(currentStep)
                if (currentStepIndex == -1) null else currentStepIndex + 1
            }
        } ?: return null

        return moreStep.getOrNull(nextStepIndex)
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

    fun updateSelectedRelationShip(relationShip: RelationShip?) = intent {
        this@ReceivedEnvelopeAddViewModel.relationShip = relationShip
        copy(
            buttonEnabled = relationShip != null,
        )
    }

    fun updateMoreStep(moreStep: List<EnvelopeAddStep>) {
        this@ReceivedEnvelopeAddViewModel.moreStep = moreStep
    }
}
