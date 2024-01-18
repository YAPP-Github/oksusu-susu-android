package com.susu.feature.received.ledgeradd.content.date

import com.susu.core.ui.base.BaseViewModel
import com.susu.core.ui.util.getSafeLocalDateTime
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class DateViewModel @Inject constructor(
) : BaseViewModel<DateState, DateSideEffect>(
    DateState(),
) {
    fun updateNameAndCategory(name: String?, categoryName: String?) = intent {
        copy(
            name = name ?: this.name,
            categoryName = categoryName ?: this.categoryName,
        )
    }

    fun updateStartDate(year: Int, month: Int, day: Int) {
        intent {
            copy(
                startAt = LocalDateTime.of(year, month, day, 0, 0),
            )
        }
    }

    fun updateEndDate(year: Int, month: Int, day: Int) = intent {
        copy(
            endAt = getSafeLocalDateTime(year, month, day),
        )
    }

    fun showStartDateBottomSheet() = intent { copy(showStartDateBottomSheet = true) }
    fun hideStartDateBottomSheet() = intent { copy(showStartDateBottomSheet = false) }
    fun showEndDateBottomSheet() = intent { copy(showEndDateBottomSheet = true) }
    fun hideEndDateBottomSheet() = intent { copy(showEndDateBottomSheet = false) }
}
