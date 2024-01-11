package com.susu.feature.loginsignup.signup

import com.susu.core.ui.base.SideEffect
import com.susu.core.ui.base.UiState

sealed class SignUpEffect : SideEffect {
    data object NavigateToLogin : SignUpEffect()
    data object NavigateToReceived : SignUpEffect()
    data class ShowToast(val msg: String) : SignUpEffect()
}

data class SignUpState(
    val isNextStepAvailable: Boolean = true,
    val currentStep: SignUpStep = SignUpStep.TERMS,
    val terms: List<Int> = emptyList(),
    val name: String = "",
    val gender: Gender = Gender.NONE,
    val birth: Int = 1930,
) : UiState

enum class SignUpStep(
    val appBarTitle: String,
    val description: String,
) {
    TERMS(
        appBarTitle = "약관 동의",
        description = "서비스 약관을 위해\n약관에 동의해주세요",
    ),
    NAME(
        appBarTitle = "",
        description = "반가워요!\n이름을 알려주세요",
    ),
    ADDITIONAL(
        appBarTitle = "",
        description = "아래 정보들을 알려주시면\n통계를 알려드릴 수 있어요",
    ),
}

enum class Gender(val content: String) {
    NONE(""), MALE("M"), FEMALE("F")
}
