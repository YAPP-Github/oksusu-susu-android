package com.susu.feature.navigator

import com.susu.core.ui.base.SideEffect
import com.susu.core.ui.base.UiState

data class MainState(
    val isNavigating: Boolean = true,
) : UiState

sealed interface MainSideEffect : SideEffect {
    data object NavigateLogin : MainSideEffect
    data object NavigateSignup : MainSideEffect
    data object NavigateSent : MainSideEffect
}
