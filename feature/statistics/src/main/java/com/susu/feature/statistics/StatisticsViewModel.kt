package com.susu.feature.statistics

import androidx.lifecycle.viewModelScope
import com.susu.core.ui.base.BaseViewModel
import com.susu.domain.usecase.statistics.CheckAdditionalUserInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StatisticsViewModel @Inject constructor(
    private val checkAdditionalUserInfoUseCase: CheckAdditionalUserInfoUseCase,
) : BaseViewModel<StatisticsState, StatisticsEffect>(StatisticsState()) {

    fun checkAdditionalInfo() {
        viewModelScope.launch {
            intent { copy(isLoading = true) }
            checkAdditionalUserInfoUseCase()
                .onSuccess {
                    if (!it) {
                        postSideEffect(StatisticsEffect.ShowAdditionalInfoDialog)
                    }
                    intent { copy(isBlind = !it) }
                }.onFailure {
                    postSideEffect(StatisticsEffect.HandleException(it, ::checkAdditionalInfo))
                }
            intent { copy(isLoading = false) }
        }
    }

    fun selectStatisticsTab(tab: StatisticsTab) = intent {
        copy(currentTab = tab)
    }
}
