package com.susu.feature.loginsignup.signup

import com.susu.core.ui.base.SideEffect
import com.susu.core.ui.base.UiState

sealed interface SignUpContract {
    sealed class SignUpEffect : SideEffect {
        data object NavigateToReceived : SignUpEffect()
        data class ShowToast(val msg: String) : SignUpEffect()
    }

    data class SignUpState(
        val name: String = "",
        val gender: String = "M",
        val birth: String = "0",
    ) : UiState
}
