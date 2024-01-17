package com.susu.feature.received.Category.category

import androidx.lifecycle.viewModelScope
import com.susu.core.model.Category
import com.susu.core.model.Ledger
import com.susu.core.ui.base.BaseViewModel
import com.susu.domain.usecase.categoryconfig.GetCategoryConfigUseCase
import com.susu.feature.received.ledgeradd.category.CategorySideEffect
import com.susu.feature.received.ledgeradd.category.CategoryState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val getCategoryConfigUseCase: GetCategoryConfigUseCase,
) : BaseViewModel<CategoryState, CategorySideEffect>(
    CategoryState(),
) {
    val parentButtonEnabled = with(currentState) {
        (selectedCategory == customCategory && !customCategory.customCategory.isNullOrEmpty()) ||
            selectedCategory != null
    }

    fun getCategoryConfig() = viewModelScope.launch {
        getCategoryConfigUseCase()
            .onSuccess {
                intent {
                    copy(
                        categoryConfig = it.dropLast(1).toPersistentList(),
                        customCategory = it.last(),
                    )
                }
            }
            .onFailure { }
    }

    fun selectCategory(category: Category) = intent { copy(selectedCategory = category) }

    fun selectCustomCategory() = intent {
        postSideEffect(CategorySideEffect.FocusCustomCategory)
        copy(selectedCategory = customCategory)
    }

    fun showCustomCategoryTextField() = intent {
        copy(
            showTextFieldButton = true,
            selectedCategory = customCategory,
        )
    }

    fun hideCustomCategoryTextField() = intent {
        copy(
            showTextFieldButton = false,
            selectedCategory = null,
            customCategory = customCategory.copy(customCategory = ""),
        )
    }

}
