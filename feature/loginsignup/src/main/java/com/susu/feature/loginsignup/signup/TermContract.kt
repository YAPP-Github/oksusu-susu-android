package com.susu.feature.loginsignup.signup

import com.susu.core.model.Term
import com.susu.core.model.TermDetail
import com.susu.core.ui.base.SideEffect
import com.susu.core.ui.base.UiState

object TermEffect : SideEffect

data class TermState(
    val isLoading: Boolean = false,
    val terms: List<Term> = emptyList(),
    val currentTerm: TermDetail = TermDetail(0, "", false, ""),
) : UiState
