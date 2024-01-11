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
            intent { copy(isLoading = true) }
            getTermsUseCase().onSuccess {
                intent { copy(terms = it, isLoading = false) }
            }.onFailure {
                // TODO: 실패
                intent { copy(isLoading = false) }
            }
        }
    }

    fun updateCurrentTerm(termId: Int) {
        viewModelScope.launch {
            intent { copy(isLoading = true) }
            getTermDetailUseCase(termId).onSuccess {
                intent { copy(currentTerm = it, isLoading = false) }
            }.onFailure {
                // TODO: 실패
                intent { copy(isLoading = false) }
            }
        }
    }
}
