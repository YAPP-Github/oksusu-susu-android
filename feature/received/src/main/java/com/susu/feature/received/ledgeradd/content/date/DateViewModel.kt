package com.susu.feature.received.ledgeradd.content.date

import com.susu.core.ui.base.BaseViewModel
import com.susu.core.ui.util.getSafeLocalDateTime
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class DateViewModel @Inject constructor() : BaseViewModel<DateState, DateSideEffect>(
    DateState(),
) {
    fun updateNameAndCategory(name: String, categoryName: String) = intent {
        copy(
            name = name,
            categoryName = categoryName,
        )
    }

    fun updateStartDate(year: Int, month: Int, day: Int) = intent {
        val toUpdateStartAt = LocalDateTime.of(year, month, day, 0, 0)
        postSideEffect(DateSideEffect.UpdateParentDate(toUpdateStartAt, endAt))
        copy(
            startAt = toUpdateStartAt,
        )
    }

    fun updateEndDate(year: Int, month: Int, day: Int) = intent {
        val toUpdateEndAt = getSafeLocalDateTime(year, month, day)
        postSideEffect(DateSideEffect.UpdateParentDate(startAt, toUpdateEndAt))
        copy(
            endAt = toUpdateEndAt,
        )
    }

    fun updateParentDate() = with(currentState) {
        postSideEffect(DateSideEffect.UpdateParentDate(startAt, endAt))
    }

    fun showStartDateBottomSheet() = intent { copy(showStartDateBottomSheet = true) }
    fun hideStartDateBottomSheet() = intent { copy(showStartDateBottomSheet = false) }
    fun showEndDateBottomSheet() = intent { copy(showEndDateBottomSheet = true) }
    fun hideEndDateBottomSheet() = intent { copy(showEndDateBottomSheet = false) }
}
