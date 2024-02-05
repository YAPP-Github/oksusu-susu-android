package com.susu.feature.received.ledgeredit

import com.susu.core.model.Category
import com.susu.core.ui.base.SideEffect
import com.susu.core.ui.base.UiState
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

data class LedgerEditState(
    val name: String = "",
    val selectedCategoryId: Int = 0,
    val customCategory: String = "",
    val startYear: Int = 0,
    val startMonth: Int = 0,
    val startDay: Int = 0,
    val endYear: Int? = null,
    val endMonth: Int? = null,
    val endDay: Int? = null,
    val categoryConfigList: PersistentList<Category> = persistentListOf(Category()),
    val showCustomCategoryButton: Boolean = false,
    val isCustomCategoryChipSaved: Boolean = false,
    val showStartDateBottomSheet: Boolean = false,
    val showEndDateBottomSheet: Boolean = false,
    val showOnlyStartDate: Boolean = false,
) : UiState {
    val isSelectedCustomCategory = selectedCategoryId == categoryConfigList.last().id
    val saveButtonEnabled = when {
        name.isEmpty() -> false
        endYear == null -> false
        isSelectedCustomCategory && (customCategory.isEmpty() || isCustomCategoryChipSaved.not()) -> false
        else -> true
    }
}

sealed interface LedgerEditSideEffect : SideEffect {
    data object PopBackStack : LedgerEditSideEffect
    data object FocusCustomCategory : LedgerEditSideEffect
}
