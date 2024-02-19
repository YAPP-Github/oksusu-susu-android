package com.susu.feature.statistics.content.my

import androidx.lifecycle.viewModelScope
import com.susu.core.model.MyStatistics
import com.susu.core.ui.base.BaseViewModel
import com.susu.domain.usecase.statistics.GetMyStatisticsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyStatisticsViewModel @Inject constructor(
    private val getMyStatisticsUseCase: GetMyStatisticsUseCase,
) : BaseViewModel<MyStatisticsState, MyStatisticsEffect>(MyStatisticsState()) {
    fun getMyStatistics(onFinish: (() -> Unit)? = null) {
        viewModelScope.launch {
            intent { copy(isLoading = onFinish == null) }
            getMyStatisticsUseCase()
                .onSuccess {
                    if (it == MyStatistics.EMPTY) {
                        postSideEffect(MyStatisticsEffect.ShowDataNotExistDialog)
                    }
                    intent { copy(statistics = it, isBlind = it == MyStatistics.EMPTY) }
                }.onFailure {
                    postSideEffect(MyStatisticsEffect.HandleException(it, ::getMyStatistics))
                }
            intent { copy(isLoading = false) }

            onFinish?.invoke()
        }
    }
}
