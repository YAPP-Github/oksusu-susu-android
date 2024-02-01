package com.susu.feature.statistics.content.my

import androidx.lifecycle.viewModelScope
import com.susu.core.ui.base.BaseViewModel
import com.susu.domain.usecase.statistics.GetMyStatisticsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyStatisticsViewModel @Inject constructor(
    private val getMyStatisticsUseCase: GetMyStatisticsUseCase,
) : BaseViewModel<MyStatisticsState, MyStatisticsEffect>(MyStatisticsState()) {
    fun getMyStatistics() {
        viewModelScope.launch {
            intent { copy(isLoading = true) }
            getMyStatisticsUseCase()
                .onSuccess {
                    intent { copy(statistics = it) }
                }.onFailure {
                    postSideEffect(MyStatisticsEffect.HandleException(it, ::getMyStatistics))
                }
            intent { copy(isLoading = false) }
        }
    }
}