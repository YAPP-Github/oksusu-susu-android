package com.susu.feature.envelopeadd.content.more

import com.susu.core.ui.base.SideEffect
import com.susu.core.ui.base.UiState
import com.susu.core.ui.R
import com.susu.feature.envelopeadd.EnvelopeAddStep
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentMapOf

data class MoreState(
    val selectedMoreStop: PersistentList<EnvelopeAddStep> = persistentListOf(),
) : UiState

val moreStep = persistentMapOf(
    EnvelopeAddStep.VISITED to R.string.word_is_visited,
    EnvelopeAddStep.PRESENT to R.string.word_gift,
    EnvelopeAddStep.MEMO to R.string.word_memo,
    EnvelopeAddStep.PHONE to com.susu.feature.sent.R.string.sent_add_more_step_phone,
)

sealed interface MoreSideEffect : SideEffect {
    data class UpdateParentMoreStep(val moreStep: List<EnvelopeAddStep>) : MoreSideEffect
}
