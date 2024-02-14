package com.susu.feature.received.ledgeradd.content.date

import com.daangn.www.betterkoreankotlin.JosaType
import com.susu.core.ui.base.SideEffect
import com.susu.core.ui.base.UiState
import com.susu.core.ui.util.currentDate
import strings.getJosa
import java.time.LocalDateTime

data class DateState(
    val name: String = "",
    val categoryName: String = "",
    val showEndAt: Boolean = true,
    val startAt: LocalDateTime? = currentDate,
    val endAt: LocalDateTime? = null,
    val showStartDateBottomSheet: Boolean = false,
    val showEndDateBottomSheet: Boolean = false,
    val showOnlyStartAt: Boolean = false,
) : UiState

@Suppress("detekt:FunctionNaming")
fun String.append은는Josa(): String {
    val josa = this.getJosa(JosaType.Type_은는)
    return if (josa.isEmpty()) {
        "${this}는"
    } else {
        this + josa
    }
}

sealed interface DateSideEffect : SideEffect {
    data class UpdateParentDate(
        val startAt: LocalDateTime?,
        val endAt: LocalDateTime? = null,
    ) : DateSideEffect
}
