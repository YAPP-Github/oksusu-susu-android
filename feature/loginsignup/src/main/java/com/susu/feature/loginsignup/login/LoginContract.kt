package com.susu.feature.loginsignup.login

import com.susu.core.ui.base.SideEffect
import com.susu.core.ui.base.UiState

sealed interface LoginContract {
    sealed class LoginEffect : SideEffect {
        data object NavigateToReceived : LoginEffect()
        data object NavigateToSignUp : LoginEffect()
        data class ShowToast(val msg: String) : LoginEffect()
    }

    data class LoginState(
        val isLoading: Boolean = false,
    ) : UiState
}
