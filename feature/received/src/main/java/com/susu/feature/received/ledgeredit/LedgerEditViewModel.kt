package com.susu.feature.received.ledgeredit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.susu.core.model.Category
import com.susu.core.model.Ledger
import com.susu.core.ui.base.BaseViewModel
import com.susu.core.ui.extension.decodeFromUri
import com.susu.core.ui.extension.encodeToUri
import com.susu.domain.usecase.categoryconfig.GetCategoryConfigUseCase
import com.susu.domain.usecase.ledger.EditLedgerUseCase
import com.susu.feature.received.navigation.ReceivedRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.launch
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toKotlinLocalDateTime
import kotlinx.serialization.json.Json
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class LedgerEditViewModel @Inject constructor(
    private val getCategoryConfigUseCase: GetCategoryConfigUseCase,
    private val editLedgerUseCase: EditLedgerUseCase,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<LedgerEditState, LedgerEditSideEffect>(
    LedgerEditState(),
) {
    private val argument = savedStateHandle.get<String>(ReceivedRoute.LEDGER_ARGUMENT_NAME)!!
    private var ledgerId = 0L
    private val toEditLedger
        get() = with(currentState) {
            Ledger(
                id = ledgerId,
                title = name,
                startAt = LocalDateTime.of(startYear, startMonth, startDay, 0, 0).toKotlinLocalDateTime(),
                endAt = LocalDateTime.of(endYear, endMonth, endDay, 0, 0).toKotlinLocalDateTime(),
                category = Category(
                    id = selectedCategoryId,
                    customCategory = customCategory.ifEmpty { null },
                ),
            )
        }

    fun editLedger() = viewModelScope.launch {
        editLedgerUseCase(toEditLedger)
            .onSuccess {
                popBackStack()
            }
            .onFailure {
            }
    }

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
            ledgerId = ledger.id
            val customCategory = ledger.category.customCategory
            copy(
                name = ledger.title,
                selectedCategoryId = ledger.category.id,
                startYear = startDate.year,
                startMonth = startDate.monthValue,
                startDay = startDate.dayOfMonth,
                endYear = endDate.year,
                endMonth = endDate.monthValue,
                endDay = endDate.dayOfMonth,
                customCategory = customCategory ?: "",
                isCustomCategoryChipSaved = customCategory.isNullOrEmpty().not(),
                showCustomCategoryButton = ledger.category.customCategory != null,
            )
        }
    }

    fun updateCategory(categoryId: Int) = intent {
        copy(selectedCategoryId = categoryId)
    }

    fun updateName(name: String) = intent { copy(name = name) }

    fun toggleCustomCategorySaved() = intent {
        copy(
            isCustomCategoryChipSaved = !isCustomCategoryChipSaved,
        )
    }

    fun updateCustomCategory(customCategory: String) = intent {
        copy(
            customCategory = customCategory,
        )
    }

    fun showCustomCategoryButton() {
        intent {
            copy(
                showCustomCategoryButton = true,
            )
        }
        updateCategory(currentState.categoryConfigList.last().id)
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
        if (currentState.isSelectedCustomCategory) {
            updateCategory(currentState.categoryConfigList.first().id)
        }
    }

    fun updateStartDate(year: Int, month: Int, day: Int) {
        intent {
            copy(
                startYear = year,
                startMonth = month,
                startDay = day,
            )
        }
    }

    fun updateEndDate(year: Int, month: Int, day: Int) = intent {
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

    fun popBackStack() = postSideEffect(LedgerEditSideEffect.PopBackStack)
}
