package com.susu.feature.envelopeadd.content.date

import com.susu.core.ui.base.SideEffect
import com.susu.core.ui.base.UiState
import java.time.LocalDateTime

data class DateState(
    val name: String = "",
    val date: LocalDateTime? = null,
    val showDateBottomSheet: Boolean = false,
) : UiState

sealed interface DateEffect : SideEffect {
    data class UpdateParentDate(val date: LocalDateTime?) : DateEffect
}
