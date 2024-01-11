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
                postSideEffect(TermEffect.ShowToast(it.message ?: "약관을 불러오지 못했어요"))
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
                postSideEffect(TermEffect.ShowToast(it.message ?: "약관 내용을 불러오지 못했어요"))
                intent { copy(isLoading = false) }
            }
        }
    }
}
