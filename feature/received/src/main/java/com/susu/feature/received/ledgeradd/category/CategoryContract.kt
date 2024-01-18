package com.susu.feature.received.ledgeradd.category

import com.susu.core.model.Category
import com.susu.core.ui.base.SideEffect
import com.susu.core.ui.base.UiState
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

data class CategoryState(
    val selectedCategory: Category? = null,
    val categoryConfig: PersistentList<Category> = persistentListOf(),
    val customCategory: Category = Category(),
    val showTextFieldButton: Boolean = false,
    val isSavedCustomCategory: Boolean = false,
) : UiState {
    val isCustomCategorySelected = customCategory == selectedCategory
}

sealed interface CategorySideEffect : SideEffect {
    data object FocusCustomCategory : CategorySideEffect
    data class UpdateParentSelectedCategory(val category: Category?) : CategorySideEffect
}
