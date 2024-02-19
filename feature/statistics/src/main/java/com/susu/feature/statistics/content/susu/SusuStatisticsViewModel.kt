package com.susu.feature.statistics.content.susu

import androidx.lifecycle.viewModelScope
import com.susu.core.model.Category
import com.susu.core.model.Relationship
import com.susu.core.model.exception.UnknownException
import com.susu.core.ui.base.BaseViewModel
import com.susu.domain.usecase.categoryconfig.GetCategoryConfigUseCase
import com.susu.domain.usecase.envelope.GetRelationShipConfigListUseCase
import com.susu.domain.usecase.statistics.CheckAdditionalUserInfoUseCase
import com.susu.domain.usecase.statistics.GetSusuStatisticsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SusuStatisticsViewModel @Inject constructor(
    private val checkAdditionalUserInfoUseCase: CheckAdditionalUserInfoUseCase,
    private val getCategoryConfigUseCase: GetCategoryConfigUseCase,
    private val getRelationShipConfigListUseCase: GetRelationShipConfigListUseCase,
    private val getSusuStatisticsUseCase: GetSusuStatisticsUseCase,
) : BaseViewModel<SusuStatisticsState, SusuStatisticsEffect>(SusuStatisticsState()) {

    fun checkAdditionalInfo() {
        viewModelScope.launch {
            intent { copy(isLoading = true) }
            checkAdditionalUserInfoUseCase()
                .onSuccess {
                    if (!it) {
                        postSideEffect(SusuStatisticsEffect.ShowAdditionalInfoDialog)
                    }
                    intent { copy(isBlind = !it) }
                }.onFailure {
                    postSideEffect(SusuStatisticsEffect.HandleException(it, ::checkAdditionalInfo))
                }
            intent { copy(isLoading = false) }
        }
    }

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
            } else {
                val exception = categoryConfigResult.exceptionOrNull()
                    ?: relationshipConfigResult.exceptionOrNull()
                    ?: UnknownException()
                postSideEffect(SusuStatisticsEffect.HandleException(exception, ::getStatisticsOption))
            }
            intent { copy(isLoading = false) }
        }
    }

    fun getSusuStatistics(onFinish: () -> Unit = {}) {
        if (currentState.isBlind) return

        if (currentState.relationship !in currentState.relationshipConfig &&
            currentState.category !in currentState.categoryConfig
        ) {
            return
        }

        viewModelScope.launch {
            getSusuStatisticsUseCase(
                age = currentState.age.name,
                relationshipId = currentState.relationship.id.toInt(),
                categoryId = currentState.category.id,
            ).onSuccess {
                intent { copy(susuStatistics = it) }
            }.onFailure {
                postSideEffect(SusuStatisticsEffect.HandleException(it, ::getSusuStatistics))
            }

            onFinish()
        }
    }

    fun selectAge(age: StatisticsAge) {
        intent { copy(age = age, isAgeSheetOpen = false) }
        postSideEffect(SusuStatisticsEffect.LogAgeOption(currentState.age))
    }
    fun selectRelationship(index: Int) {
        intent { copy(relationship = relationshipConfig[index], isRelationshipSheetOpen = false) }
        postSideEffect(SusuStatisticsEffect.LogRelationshipOption(currentState.relationship.relation))
    }

    fun selectCategory(index: Int) {
        intent { copy(category = categoryConfig[index], isCategorySheetOpen = false) }
        postSideEffect(SusuStatisticsEffect.LogCategoryOption(currentState.category.category))
    }

    fun showAgeSheet() = intent { copy(isAgeSheetOpen = true) }
    fun showRelationshipSheet() = intent { copy(isRelationshipSheetOpen = true) }
    fun showCategorySheet() = intent { copy(isCategorySheetOpen = true) }
    fun hideAgeSheet() = intent { copy(isAgeSheetOpen = false) }
    fun hideRelationshipSheet() = intent { copy(isRelationshipSheetOpen = false) }
    fun hideCategorySheet() = intent { copy(isCategorySheetOpen = false) }
}
