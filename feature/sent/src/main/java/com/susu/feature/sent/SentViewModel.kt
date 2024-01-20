package com.susu.feature.sent

import androidx.lifecycle.viewModelScope
import com.susu.core.ui.base.BaseViewModel
import com.susu.domain.usecase.envelope.GetEnvelopesListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SentViewModel @Inject constructor(
    private val getEnvelopesListUseCase: GetEnvelopesListUseCase,
) : BaseViewModel<SentState, SentSideEffect>(
    SentState(),
) {
    private var page = 0

    fun getEnvelopesList() = viewModelScope.launch {
        // TODO: 리스트 불러오기
    }
}
