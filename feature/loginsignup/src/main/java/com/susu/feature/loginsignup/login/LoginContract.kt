package com.susu.feature.loginsignup.login

import com.susu.core.ui.base.SideEffect
import com.susu.core.ui.base.UiState

class LoginContract {
    sealed class LoginEffect : SideEffect {
        data object NavigateToReceived : LoginEffect()
        data object NavigateToSignUp : LoginEffect()
        data class ExitProcess(val error: Throwable?) : LoginEffect()
    }

    object LoginState : UiState
}
