package com.susu.feature.envelopeadd.content.category

import androidx.lifecycle.viewModelScope
import com.susu.core.model.Category
import com.susu.core.ui.base.BaseViewModel
import com.susu.domain.usecase.categoryconfig.GetCategoryConfigUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val getCategoryConfigUseCase: GetCategoryConfigUseCase,
) : BaseViewModel<CategoryState, CategoryEffect>(CategoryState()) {

    private val parentSelectedCategory
        get() = with(currentState) {
            if (selectedCategory == customCategory &&
                (customCategory.customCategory.isNullOrEmpty() || isSavedCustomCategory.not())
            ) {
                null
            } else {
                selectedCategory
            }
        }

    fun getCategoryConfig() = viewModelScope.launch {
        if (currentState.categoryConfig.isNotEmpty()) return@launch

        getCategoryConfigUseCase().onSuccess {
            intent {
                copy(
                    categoryConfig = it.dropLast(1).toPersistentList(),
                )
            }
        }
    }

    fun selectCategory(category: Category) = intent { copy(selectedCategory = category) }

    fun selectCustomCategory() = intent {
        postSideEffect(CategoryEffect.FocusCustomCategory)
        copy(selectedCategory = customCategory)
    }

    fun updateCustomCategoryText(text: String) = intent {
        copy(
            selectedCategory = customCategory.copy(name = text, customCategory = text),
            customCategory = customCategory.copy(name = text, customCategory = text),
        )
    }

    fun showCustomCategoryTextField() = intent {
        copy(
            showTextFieldButton = true,
            selectedCategory = customCategory,
        )
    }

    fun hideCustomCategoryTextField() = intent {
        copy(
            isSavedCustomCategory = false,
            showTextFieldButton = false,
            selectedCategory = if (isCustomCategorySelected) null else selectedCategory,
            customCategory = customCategory.copy(name = ""),
        )
    }

    fun toggleTextFieldSaved() = intent {
        copy(
            isSavedCustomCategory = !isSavedCustomCategory,
        )
    }

    fun updateParentCategory(category: Category? = parentSelectedCategory) {
        postSideEffect(CategoryEffect.UpdateParentCategory(category))
    }
}
