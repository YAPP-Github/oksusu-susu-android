package com.susu.feature.envelopeadd.content.more

import com.susu.core.ui.base.SideEffect
import com.susu.core.ui.base.UiState
import com.susu.feature.envelopeadd.EnvelopeAddStep
import com.susu.feature.sent.R
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentMapOf

data class MoreState(
    val selectedMoreStop: PersistentList<EnvelopeAddStep> = persistentListOf(),
) : UiState

val moreStep = persistentMapOf(
    EnvelopeAddStep.VISITED to R.string.sent_envelope_edit_category_visited,
    EnvelopeAddStep.PRESENT to R.string.sent_envelope_edit_category_present,
    EnvelopeAddStep.MEMO to R.string.sent_envelope_edit_category_memo,
    EnvelopeAddStep.PHONE to R.string.sent_envelope_edit_category_phone,
)

sealed interface MoreSideEffect : SideEffect {
    data class UpdateParentMoreStep(val moreStep: List<EnvelopeAddStep>) : MoreSideEffect
}
