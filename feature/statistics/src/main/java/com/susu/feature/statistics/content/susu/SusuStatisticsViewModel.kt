package com.susu.feature.statistics.content.susu

import androidx.lifecycle.viewModelScope
import com.susu.core.ui.base.BaseViewModel
import com.susu.domain.usecase.statistics.GetStatisticsOptionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SusuStatisticsViewModel @Inject constructor(
    private val getStatisticsOptionUseCase: GetStatisticsOptionUseCase,
) : BaseViewModel<SusuStatisticsState, SusuStatisticsEffect>(SusuStatisticsState()) {

    fun getStatisticsOption() {
        viewModelScope.launch {
            intent { copy(isLoading = true) }
            getStatisticsOptionUseCase().onSuccess {
                intent { copy(statisticsOption = it) }
            }.onFailure {
                postSideEffect(SusuStatisticsEffect.HandleException(it, ::getStatisticsOption))
            }
            intent { copy(isLoading = false) }
        }
    }

    fun selectAge(age: StatisticsAge) = intent { copy(age = age, isAgeSheetOpen = false) }
    fun selectRelationship(id: Int) = intent { copy(relationshipId = id, isRelationshipSheetOpen = false) }
    fun selectCategory(id: Int) = intent { copy(categoryId = id, isCategorySheetOpen = false) }

    fun showAgeSheet() = intent { copy(isAgeSheetOpen = true) }
    fun showRelationshipSheet() = intent { copy(isRelationshipSheetOpen = true) }
    fun showCategorySheet() = intent { copy(isCategorySheetOpen = true) }
    fun hideAgeSheet() = intent { copy(isAgeSheetOpen = false) }
    fun hideRelationshipSheet() = intent { copy(isRelationshipSheetOpen = false) }
    fun hideCategorySheet() = intent { copy(isCategorySheetOpen = false) }
}
