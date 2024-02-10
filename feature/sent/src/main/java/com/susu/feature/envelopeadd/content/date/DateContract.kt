package com.susu.feature.envelopeadd.content.date

import com.susu.core.ui.base.SideEffect
import com.susu.core.ui.base.UiState
import com.susu.core.ui.util.currentDate
import java.time.LocalDateTime

data class DateState(
    val name: String = "",
    val date: LocalDateTime? = currentDate,
    val showDateBottomSheet: Boolean = true,
) : UiState

sealed interface DateEffect : SideEffect {
    data class UpdateParentDate(val date: LocalDateTime?) : DateEffect
}
