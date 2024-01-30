package com.susu.feature.envelopeadd.content.more

import com.susu.core.ui.base.BaseViewModel
import com.susu.feature.envelopeadd.EnvelopeAddStep
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import javax.inject.Inject

@HiltViewModel
class MoreViewModel @Inject constructor() : BaseViewModel<MoreState, MoreSideEffect>(
    MoreState(),
) {
    fun toggleStep(envelopeAddStep: EnvelopeAddStep) = intent {
        val newStep = if (envelopeAddStep in selectedMoreStop) {
            selectedMoreStop.minus(envelopeAddStep)
        } else {
            selectedMoreStop.plus(envelopeAddStep)
        }

        postSideEffect(MoreSideEffect.UpdateParentMoreStep(newStep.sortedBy { it.ordinal }))
        copy(
            selectedMoreStop = newStep.toPersistentList(),
        )
    }
}
