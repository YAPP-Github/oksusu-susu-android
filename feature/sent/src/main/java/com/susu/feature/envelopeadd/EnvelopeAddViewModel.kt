package com.susu.feature.envelopeadd

import com.susu.core.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EnvelopeAddViewModel @Inject constructor() : BaseViewModel<EnvelopeAddState, EnvelopeAddEffect>(EnvelopeAddState()) {

    fun goNextStep() {
        when (uiState.value.currentStep) {
            EnvelopeAddStep.MONEY -> intent { copy(currentStep = EnvelopeAddStep.NAME) }
            EnvelopeAddStep.NAME -> {
                intent {
                    if (uiState.value.friendId == null) {
                        copy(currentStep = EnvelopeAddStep.RELATIONSHIP)
                    } else {
                        copy(currentStep = EnvelopeAddStep.EVENT)
                    }
                }
            }

            EnvelopeAddStep.RELATIONSHIP -> intent { copy(currentStep = EnvelopeAddStep.EVENT) }
            EnvelopeAddStep.EVENT -> intent { copy(currentStep = EnvelopeAddStep.DATE) }
            EnvelopeAddStep.DATE -> intent { copy(currentStep = EnvelopeAddStep.MORE) }
            EnvelopeAddStep.MORE -> postSideEffect(EnvelopeAddEffect.CompleteEnvelopeAdd)
            else -> intent { copy(currentStep = EnvelopeAddStep.MORE) }
        }
    }

    fun goPrevStep() {
        when (uiState.value.currentStep) {
            EnvelopeAddStep.MONEY -> postSideEffect(EnvelopeAddEffect.PopBackStack)
            EnvelopeAddStep.NAME -> intent { copy(currentStep = EnvelopeAddStep.MONEY) }
            EnvelopeAddStep.RELATIONSHIP -> intent { copy(currentStep = EnvelopeAddStep.NAME) }
            EnvelopeAddStep.EVENT -> {
                intent {
                    if (uiState.value.friendId == null) {
                        copy(currentStep = EnvelopeAddStep.RELATIONSHIP)
                    } else {
                        copy(currentStep = EnvelopeAddStep.NAME)
                    }
                }
            }

            EnvelopeAddStep.DATE -> intent { copy(currentStep = EnvelopeAddStep.EVENT) }
            EnvelopeAddStep.MORE -> intent { copy(currentStep = EnvelopeAddStep.DATE) }
            else -> intent { copy(currentStep = EnvelopeAddStep.MORE) }
        }
    }

}
