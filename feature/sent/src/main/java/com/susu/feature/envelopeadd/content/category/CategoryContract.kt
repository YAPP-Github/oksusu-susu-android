package com.susu.feature.envelopeadd.content.category

import com.susu.core.model.Category
import com.susu.core.ui.base.SideEffect
import com.susu.core.ui.base.UiState
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

data class CategoryState(
    val selectedCategory: Category? = null,
    val categoryConfig: PersistentList<Category> = persistentListOf(),
    val customCategory: Category = Category(id = 5),
    val showTextFieldButton: Boolean = false,
    val isSavedCustomCategory: Boolean = false,
) : UiState {
    val isCustomCategorySelected = customCategory == selectedCategory
}

sealed interface CategoryEffect : SideEffect {
    data object FocusCustomCategory : CategoryEffect
    data class UpdateParentCategory(val category: Category?) : CategoryEffect
}
