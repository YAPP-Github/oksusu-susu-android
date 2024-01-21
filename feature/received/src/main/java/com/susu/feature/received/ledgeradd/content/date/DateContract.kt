package com.susu.feature.received.ledgeradd.content.date

import com.susu.core.ui.base.SideEffect
import com.susu.core.ui.base.UiState
import java.time.LocalDateTime

data class DateState(
    val name: String = "",
    val categoryName: String = "",
    val showEndAt: Boolean = true,
    val startAt: LocalDateTime? = null,
    val endAt: LocalDateTime? = null,
    val showStartDateBottomSheet: Boolean = false,
    val showEndDateBottomSheet: Boolean = false,
) : UiState

sealed interface DateSideEffect : SideEffect {
    data class UpdateParentDate(
        val startAt: LocalDateTime?,
        val endAt: LocalDateTime? = null,
    ) : DateSideEffect
}
