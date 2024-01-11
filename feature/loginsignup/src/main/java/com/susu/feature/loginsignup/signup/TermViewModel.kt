package com.susu.feature.loginsignup.signup

import androidx.lifecycle.viewModelScope
import com.susu.core.ui.base.BaseViewModel
import com.susu.domain.usecase.loginsignup.GetTermDetailUseCase
import com.susu.domain.usecase.loginsignup.GetTermsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TermViewModel @Inject constructor(
    private val getTermsUseCase: GetTermsUseCase,
    private val getTermDetailUseCase: GetTermDetailUseCase,
) : BaseViewModel<TermState, TermEffect>(TermState()) {

    init {
        viewModelScope.launch {
            getTermsUseCase().onSuccess {
                intent { copy(terms = it) }
            }.onFailure {
                // TODO: 실패
            }
        }
    }

    fun updateCurrentTerm(termId: Int) {
        viewModelScope.launch {
            getTermDetailUseCase(termId).onSuccess {
                intent { copy(currentTerm = it) }
            }.onFailure {
                // TODO: 실패
            }
        }
    }
}
