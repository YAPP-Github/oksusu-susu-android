package com.susu.feature.loginsignup.login

import com.susu.core.model.OnboardVote
import com.susu.core.ui.base.SideEffect
import com.susu.core.ui.base.UiState

sealed interface LoginEffect : SideEffect {
    data object NavigateToReceived : LoginEffect
    data object NavigateToSignUp : LoginEffect
    data class ShowSnackBar(val message: String) : LoginEffect
}

data class LoginState(
    val isLoading: Boolean = false,
    val onboardVote: OnboardVote? = null,
) : UiState
