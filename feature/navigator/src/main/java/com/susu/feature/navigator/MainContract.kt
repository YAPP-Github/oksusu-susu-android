package com.susu.feature.navigator

import com.susu.core.ui.DialogToken
import com.susu.core.ui.SnackbarToken
import com.susu.core.ui.base.SideEffect
import com.susu.core.ui.base.UiState

data class MainState(
    val isNavigating: Boolean = true,
    val snackbarToken: SnackbarToken = SnackbarToken(),
    val snackbarVisible: Boolean = false,
    val dialogToken: DialogToken = DialogToken(),
    val dialogVisible: Boolean = false,
) : UiState

sealed interface MainSideEffect : SideEffect {
    data object NavigateLogin : MainSideEffect
    data object NavigateSignup : MainSideEffect
    data object NavigateSent : MainSideEffect
}
