package com.susu.feature.envelopeadd

import com.susu.core.ui.base.SideEffect
import com.susu.core.ui.base.UiState
import com.susu.core.ui.util.currentDate
import java.time.LocalDateTime

sealed interface EnvelopeAddEffect : SideEffect {
    data object PopBackStack : EnvelopeAddEffect
    data object CompleteEnvelopeAdd : EnvelopeAddEffect
}

data class EnvelopeAddState(
    val isLoading: Boolean = false,
    val currentStep: EnvelopeAddStep = EnvelopeAddStep.MONEY,
    val amount: Int = 0,
    val friendId: Int? = null,
    val friendName: String = "",
    val searchedFriends: List<String> = emptyList(), // TODO: data class Friend
    val categoryId: Int = 0,
    val customCategory: String = "",
    val friendPhoneNumber: String? = null,
    val relationshipId: Int = 0,
    val customRelationship: String? = null,
    val handedOverAt: LocalDateTime = currentDate,
    val hasVisited: Boolean? = null,
    val memo: String? = null,
    val present: String? = null,
) : UiState
