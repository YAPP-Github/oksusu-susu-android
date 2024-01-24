package com.susu.feature.received.envelopeadd.content.more

import com.susu.core.ui.base.SideEffect
import com.susu.core.ui.base.UiState
import com.susu.feature.received.envelopeadd.EnvelopeAddStep
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

data class MoreState(
    val selectedMoreStop: PersistentList<EnvelopeAddStep> = persistentListOf(),
) : UiState

val moreStep = persistentListOf(
    EnvelopeAddStep.VISITED,
    EnvelopeAddStep.PRESENT,
    EnvelopeAddStep.MEMO,
    EnvelopeAddStep.PHONE,
)

sealed interface MoreSideEffect : SideEffect {
    data class UpdateParentMoreStep(val moreStep: List<EnvelopeAddStep>) : MoreSideEffect
}
