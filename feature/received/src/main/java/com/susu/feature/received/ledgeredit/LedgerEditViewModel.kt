package com.susu.feature.received.ledgeredit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.susu.core.model.Ledger
import com.susu.core.ui.base.BaseViewModel
import com.susu.core.ui.extension.decodeFromUri
import com.susu.domain.usecase.categoryconfig.GetCategoryConfigUseCase
import com.susu.feature.received.navigation.ReceivedRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.launch
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.serialization.json.Json
import javax.inject.Inject

@HiltViewModel
class LedgerEditViewModel @Inject constructor(
    private val getCategoryConfigUseCase: GetCategoryConfigUseCase,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<LedgerEditState, LedgerEditSideEffect>(
    LedgerEditState(),
) {
    private val argument = savedStateHandle.get<String>(ReceivedRoute.LEDGER_ARGUMENT_NAME)!!
    private val customCategoryId
        get() = currentState.categoryConfigList.last().id

    fun initData() = viewModelScope.launch {
        getCategoryConfigUseCase()
            .onSuccess {
                intent { copy(categoryConfigList = it.toPersistentList()) }
                initLedger()
            }
            .onFailure { }
    }

    private fun initLedger() {
        val ledger = Json.decodeFromUri<Ledger>(argument)
        val (startDate, endDate) = (ledger.startAt.toJavaLocalDateTime() to ledger.endAt.toJavaLocalDateTime())
        intent {
            copy(
                name = ledger.title,
                selectedCategory = ledger.category,
                startYear = startDate.year,
                startMonth = startDate.monthValue,
                startDay = startDate.dayOfMonth,
                endYear = endDate.year,
                endMonth = endDate.monthValue,
                endDay = endDate.dayOfMonth,
                showCustomCategoryButton = ledger.category.customCategory != null,
            )
        }
    }

    fun updateCategory(categoryId: Int) = intent {
        copy(selectedCategory = selectedCategory.copy(id = categoryId))
    }

    fun updateName(name: String) = intent { copy(name = name) }

    fun toggleCustomCategorySaved() = intent {
        copy(
            isCustomCategoryChipSaved = !isCustomCategoryChipSaved,
        )
    }

    fun updateCustomCategory(customCategory: String) = intent {
        copy(
            selectedCategory = selectedCategory.copy(
                customCategory = customCategory,
            ),
        )
    }

    fun showCustomCategoryButton() {
        intent {
            copy(
                showCustomCategoryButton = true,
            )
        }
        updateCategory(customCategoryId)
        postSideEffect(LedgerEditSideEffect.FocusCustomCategory)
    }

    fun hideCustomCategoryButton() {
        intent {
            copy(
                showCustomCategoryButton = false,
                isCustomCategoryChipSaved = false,
            )
        }
        updateCustomCategory("")
        if(currentState.selectedCategory.id == customCategoryId) {
            updateCategory(currentState.categoryConfigList.first().id)
        }
    }

    fun updateStartYear(year: Int, month: Int, day: Int) {
        intent {
            copy(
                startYear = year,
                startMonth = month,
                startDay = day,
            )
        }
    }

    fun updateEndYear(year: Int, month: Int, day: Int) = intent {
        copy(
            endYear = year,
            endMonth = month,
            endDay = day,
        )
    }

    fun showStartDateBottomSheet() = intent { copy(showStartDateBottomSheet = true) }
    fun hideStartDateBottomSheet() = intent { copy(showStartDateBottomSheet = false) }
    fun showEndDateBottomSheet() = intent { copy(showEndDateBottomSheet = true) }
    fun hideEndDateBottomSheet() = intent { copy(showEndDateBottomSheet = false) }

    fun popBackStack() = postSideEffect(LedgerEditSideEffect.popBackStack)
}
