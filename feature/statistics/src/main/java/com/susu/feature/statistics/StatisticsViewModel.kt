package com.susu.feature.statistics

import androidx.lifecycle.viewModelScope
import com.susu.core.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StatisticsViewModel @Inject constructor() : BaseViewModel<StatisticsState, StatisticsEffect>(StatisticsState()) {

    fun checkAdditionalInfo() {
        viewModelScope.launch {
            intent { copy(isLoading = true) }
            // TODO: 정보 입력 여부 확인
            intent { copy(isLoading = false) }
        }
    }

    fun selectStatisticsTab(tab: StatisticsTab) = intent {
        copy(currentTab = tab)
    }
}
