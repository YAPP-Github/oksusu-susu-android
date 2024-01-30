package com.susu.feature.envelopeadd.content.date

import com.susu.core.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class DateViewModel @Inject constructor() : BaseViewModel<DateState, DateEffect>(
    DateState(),
) {
    fun updateName(name: String) = intent { copy(name = name) }

    fun updateDate(year: Int, month: Int, day: Int) = intent {
        val toUpdateDate = LocalDateTime.of(year, month, day, 0, 0)
        postSideEffect(DateEffect.UpdateParentDate(toUpdateDate))
        copy(
            date = toUpdateDate,
        )
    }

    fun showDateBottomSheet() = intent { copy(showDateBottomSheet = true) }
    fun hideDateBottomSheet() = intent { copy(showDateBottomSheet = false) }
}
