package com.susu.feature.received.envelopeadd.content.date

import com.susu.core.ui.base.SideEffect
import com.susu.core.ui.base.UiState
import java.time.LocalDateTime

data class DateState(
    val name: String = "",
    val date: LocalDateTime? = null,
    val showDateBottomSheet: Boolean = false,
) : UiState

sealed interface DateSideEffect : SideEffect {
    data class UpdateParentDate(val date: LocalDateTime?) : DateSideEffect
}
