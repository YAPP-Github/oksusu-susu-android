package com.susu.feature.loginsignup.signup

import androidx.annotation.StringRes
import com.susu.core.ui.base.SideEffect
import com.susu.core.ui.base.UiState
import com.susu.feature.loginsignup.R

sealed class SignUpEffect : SideEffect {
    data object NavigateToLogin : SignUpEffect()
    data object NavigateToReceived : SignUpEffect()
    data class ShowToast(val msg: String) : SignUpEffect()
}

data class SignUpState(
    val currentStep: SignUpStep = SignUpStep.TERMS,
    val agreedTerms: List<Int> = emptyList(),
    val name: String = "",
    val isNameValid: Boolean = true,
    val gender: Gender = Gender.NONE,
    val birth: Int = -1,
) : UiState

enum class SignUpStep(
    @StringRes val appBarTitle: Int?,
    @StringRes val description: Int?,
    @StringRes val bottomButtonText: Int,
) {
    TERMS(
        appBarTitle = R.string.signup_term_title,
        description = R.string.signup_term_description,
        bottomButtonText = com.susu.core.ui.R.string.word_next,
    ),
    TERM_DETAIL(
        appBarTitle = R.string.signup_term_detail_title,
        description = null,
        bottomButtonText = R.string.signup_term_agree,
    ),
    NAME(
        appBarTitle = null,
        description = R.string.signup_name_description,
        bottomButtonText = com.susu.core.ui.R.string.word_next,
    ),
    ADDITIONAL(
        appBarTitle = null,
        description = R.string.signup_additional_description,
        bottomButtonText = com.susu.core.ui.R.string.word_next,
    ),
}

enum class Gender(val content: String) {
    NONE(""), MALE("M"), FEMALE("F")
}
