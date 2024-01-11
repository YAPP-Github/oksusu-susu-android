package com.susu.feature.loginsignup.signup

import com.susu.core.ui.base.SideEffect
import com.susu.core.ui.base.UiState

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
    val appBarTitle: String,
    val description: String,
    val bottomButtonText: String,
) {
    TERMS(
        appBarTitle = "약관 동의",
        description = "서비스 약관을 위해\n약관에 동의해주세요",
        bottomButtonText = "다음",
    ),
    TERM_DETAIL(
        appBarTitle = "서비스 이용 약관",
        description = "",
        bottomButtonText = "동의하기",
    ),
    NAME(
        appBarTitle = "",
        description = "반가워요!\n이름을 알려주세요",
        bottomButtonText = "다음",
    ),
    ADDITIONAL(
        appBarTitle = "",
        description = "아래 정보들을 알려주시면\n통계를 알려드릴 수 있어요",
        bottomButtonText = "다음",
    ),
}

enum class Gender(val content: String) {
    NONE(""), MALE("M"), FEMALE("F")
}
