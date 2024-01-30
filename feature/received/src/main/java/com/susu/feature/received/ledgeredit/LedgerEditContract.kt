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
    val endYear: Int = 0,
    val endMonth: Int = 0,
    val endDay: Int = 0,
    val categoryConfigList: PersistentList<Category> = persistentListOf(Category()),
    val showCustomCategoryButton: Boolean = false,
    val isCustomCategoryChipSaved: Boolean = false,
    val showStartDateBottomSheet: Boolean = false,
    val showEndDateBottomSheet: Boolean = false,
) : UiState {
    val isSelectedCustomCategory = selectedCategoryId == categoryConfigList.last().id
    val saveButtonEnabled = when {
        name.isEmpty() -> false
        isSelectedCustomCategory && (customCategory.isEmpty() || isCustomCategoryChipSaved.not()) -> false
        else -> true
    }
}

sealed interface LedgerEditSideEffect : SideEffect {
    data object PopBackStack : LedgerEditSideEffect
    data object FocusCustomCategory : LedgerEditSideEffect
}
