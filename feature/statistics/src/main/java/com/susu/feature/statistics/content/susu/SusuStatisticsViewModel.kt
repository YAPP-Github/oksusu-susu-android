package com.susu.feature.statistics.content.susu

import androidx.lifecycle.viewModelScope
import com.susu.core.model.Category
import com.susu.core.model.Relationship
import com.susu.core.ui.base.BaseViewModel
import com.susu.domain.usecase.categoryconfig.GetCategoryConfigUseCase
import com.susu.domain.usecase.envelope.GetRelationShipConfigListUseCase
import com.susu.domain.usecase.statistics.GetSusuStatisticsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SusuStatisticsViewModel @Inject constructor(
    private val getCategoryConfigUseCase: GetCategoryConfigUseCase,
    private val getRelationShipConfigListUseCase: GetRelationShipConfigListUseCase,
    private val getSusuStatisticsUseCase: GetSusuStatisticsUseCase,
) : BaseViewModel<SusuStatisticsState, SusuStatisticsEffect>(SusuStatisticsState()) {

    fun getStatisticsOption() {
        viewModelScope.launch {
            intent { copy(isLoading = true) }
            val categoryConfigResult = getCategoryConfigUseCase()
            val relationshipConfigResult = getRelationShipConfigListUseCase()

            if (categoryConfigResult.isSuccess && relationshipConfigResult.isSuccess) {
                val categoryConfig = categoryConfigResult.getOrDefault(emptyList()).toPersistentList()
                val relationshipConfig = relationshipConfigResult.getOrDefault(emptyList()).toPersistentList()
                intent {
                    copy(
                        categoryConfig = categoryConfig,
                        relationshipConfig = relationshipConfig,
                        category = categoryConfig.firstOrNull() ?: Category(),
                        relationship = relationshipConfig.firstOrNull() ?: Relationship(),
                    )
                }
            }
            intent { copy(isLoading = false) }
        }
    }

    fun getSusuStatistics() {
        if (currentState.relationship !in currentState.relationshipConfig &&
            currentState.category !in currentState.categoryConfig
        ) return

        viewModelScope.launch {
            getSusuStatisticsUseCase(
                age = currentState.age.name,
                relationshipId = currentState.relationship.id.toInt(),
                categoryId = currentState.category.id,
            ).onSuccess {
                println(it)
                intent { copy(susuStatistics = it) }
            }.onFailure {
                postSideEffect(SusuStatisticsEffect.HandleException(it, ::getSusuStatistics))
            }
        }
    }

    fun selectAge(age: StatisticsAge) = intent { copy(age = age, isAgeSheetOpen = false) }
    fun selectRelationship(index: Int) = intent { copy(relationship = relationshipConfig[index], isRelationshipSheetOpen = false) }
    fun selectCategory(index: Int) = intent { copy(category = categoryConfig[index], isCategorySheetOpen = false) }
    fun showAgeSheet() = intent { copy(isAgeSheetOpen = true) }
    fun showRelationshipSheet() = intent { copy(isRelationshipSheetOpen = true) }
    fun showCategorySheet() = intent { copy(isCategorySheetOpen = true) }
    fun hideAgeSheet() = intent { copy(isAgeSheetOpen = false) }
    fun hideRelationshipSheet() = intent { copy(isRelationshipSheetOpen = false) }
    fun hideCategorySheet() = intent { copy(isCategorySheetOpen = false) }
}
