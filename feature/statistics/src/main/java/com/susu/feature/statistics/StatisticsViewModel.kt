package com.susu.feature.statistics

import com.susu.core.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StatisticsViewModel @Inject constructor() : BaseViewModel<StatisticsState, StatisticsEffect>(StatisticsState()) {

    fun selectStatisticsTab(tab: StatisticsTab) = intent {
        copy(currentTab = tab)
    }
}
