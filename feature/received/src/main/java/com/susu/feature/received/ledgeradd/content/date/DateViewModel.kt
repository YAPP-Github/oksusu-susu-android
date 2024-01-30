package com.susu.feature.received.ledgeradd.content.date

import androidx.lifecycle.viewModelScope
import com.susu.core.model.Category
import com.susu.core.ui.base.BaseViewModel
import com.susu.core.ui.util.getSafeLocalDateTime
import com.susu.domain.usecase.ledger.GetCreateLedgerConfigUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class DateViewModel @Inject constructor(
    private val getCreateLedgerConfigUseCase: GetCreateLedgerConfigUseCase,
) : BaseViewModel<DateState, DateSideEffect>(
    DateState(),
) {
    private var category = Category()

    fun setScreenType(category: Category) = viewModelScope.launch {
        if (this@DateViewModel.category == category) return@launch

        getCreateLedgerConfigUseCase()
            .onSuccess { onlyStartAtCategoryIdList ->
                intent { copy(showOnlyStartAt = category.id in onlyStartAtCategoryIdList) }
            }
    }

    fun updateNameAndCategory(name: String, categoryName: String) = intent {
        copy(
            name = name,
            categoryName = categoryName,
        )
    }

    fun updateStartDate(year: Int, month: Int, day: Int) = intent {
        val toUpdateStartAt = LocalDateTime.of(year, month, day, 0, 0)
        val toUpdateEndAt = if (showOnlyStartAt) toUpdateStartAt else endAt
        postSideEffect(DateSideEffect.UpdateParentDate(toUpdateStartAt, toUpdateEndAt))
        copy(
            startAt = toUpdateStartAt,
            endAt = toUpdateEndAt,
        )
    }

    fun updateEndDate(year: Int, month: Int, day: Int) = intent {
        val toUpdateEndAt = getSafeLocalDateTime(year, month, day)
        postSideEffect(DateSideEffect.UpdateParentDate(startAt, toUpdateEndAt))
        copy(
            endAt = toUpdateEndAt,
        )
    }

    fun showStartDateBottomSheet() = intent { copy(showStartDateBottomSheet = true) }
    fun hideStartDateBottomSheet() = intent { copy(showStartDateBottomSheet = false) }
    fun showEndDateBottomSheet() = intent { copy(showEndDateBottomSheet = true) }
    fun hideEndDateBottomSheet() = intent { copy(showEndDateBottomSheet = false) }
    fun toggleShowOnlyStartAt() = intent {
        if (showOnlyStartAt) {
            postSideEffect(DateSideEffect.UpdateParentDate(startAt, null))
            copy(
                showOnlyStartAt = false,
                endAt = null,
            )
        } else {
            postSideEffect(DateSideEffect.UpdateParentDate(startAt, startAt))
            copy(
                showOnlyStartAt = true,
                endAt = startAt,
            )
        }
    }
}
