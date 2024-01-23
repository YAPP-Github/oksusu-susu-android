package com.susu.feature.received.ledgerfilter

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.susu.core.model.Category
import com.susu.core.ui.base.BaseViewModel
import com.susu.core.ui.extension.decodeFromUri
import com.susu.core.ui.extension.encodeToUri
import com.susu.domain.usecase.categoryconfig.GetCategoryConfigUseCase
import com.susu.feature.received.navigation.ReceivedRoute
import com.susu.feature.received.navigation.argument.FilterArgument
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.minus
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.launch
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toKotlinLocalDateTime
import kotlinx.serialization.json.Json
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class LedgerFilterViewModel @Inject constructor(
    private val getCategoryConfigUseCase: GetCategoryConfigUseCase,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<LedgerFilterState, LedgerFilterSideEffect>(
    LedgerFilterState(),
) {
    private val argument = savedStateHandle.get<String>(ReceivedRoute.FILTER_ARGUMENT_NAME)!!
    private var filter = FilterArgument()

    fun initData() {
        initFilter()
        initCategoryConfig()
    }

    private fun initFilter() {
        filter = Json.decodeFromUri(argument)
        intent {
            copy(
                selectedCategoryList = filter.selectedCategoryList.toPersistentList(),
                startAt = filter.startAt?.toJavaLocalDateTime(),
                endAt = filter.endAt?.toJavaLocalDateTime(),
            )
        }
    }

    private fun initCategoryConfig() = viewModelScope.launch {
        getCategoryConfigUseCase()
            .onSuccess {
                intent { copy(categoryConfig = it.toPersistentList()) }
            }
    }

    fun updateStartDate(year: Int, month: Int, day: Int) {
        intent {
            copy(
                startAt = LocalDateTime.of(year, month, day, 0, 0),
            )
        }
    }

    fun updateEndDate(year: Int, month: Int, day: Int) = intent {
        copy(
            endAt = LocalDateTime.of(year, month, day, 0, 0),
        )
    }

    fun clearFilter() = intent {
        copy(
            startAt = null,
            endAt = null,
            selectedCategoryList = persistentListOf(),
        )
    }

    fun clearDate() = intent { copy(startAt = null, endAt = null) }
    fun selectCategory(category: Category) = intent {
        if (category in selectedCategoryList) return@intent this
        copy(
            selectedCategoryList = selectedCategoryList.plus(category).toPersistentList(),
        )
    }

    fun unselectCategory(category: Category) = intent {
        if (category !in selectedCategoryList) return@intent this
        copy(
            selectedCategoryList = selectedCategoryList.minus(category).toPersistentList(),
        )
    }

    fun showStartDateBottomSheet() = intent { copy(showStartDateBottomSheet = true) }
    fun hideStartDateBottomSheet() = intent { copy(showStartDateBottomSheet = false) }
    fun showEndDateBottomSheet() = intent { copy(showEndDateBottomSheet = true) }
    fun hideEndDateBottomSheet() = intent { copy(showEndDateBottomSheet = false) }
    fun popBackStack() = postSideEffect(LedgerFilterSideEffect.PopBackStack)
    fun popBackStackWithFilter() {
        val filter = FilterArgument(
            selectedCategoryList = currentState.selectedCategoryList,
            startAt = currentState.startAt?.toKotlinLocalDateTime(),
            endAt = currentState.endAt?.toKotlinLocalDateTime(),
        )

        postSideEffect(LedgerFilterSideEffect.PopBackStackWithFilter(Json.encodeToUri(filter)))
    }
}
