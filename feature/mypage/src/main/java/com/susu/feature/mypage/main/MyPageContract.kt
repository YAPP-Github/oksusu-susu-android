package com.susu.feature.mypage.main

import com.susu.core.ui.base.SideEffect
import com.susu.core.ui.base.UiState

sealed interface MyPageEffect : SideEffect {
    data object NavigateToLogin : MyPageEffect
    data object NavigateToInfo : MyPageEffect
    data object NavigateToSocial : MyPageEffect
    data class ShowSnackbar(val msg: String) : MyPageEffect
    data object ShowLogoutDialog : MyPageEffect
    data object ShowWithdrawDialog : MyPageEffect
    data object ShowExportDialog : MyPageEffect
    data object ShowLogoutSuccessSnackbar : MyPageEffect
    data object ShowWithdrawSuccessSnackbar : MyPageEffect
    data object ShowExportSuccessSnackbar : MyPageEffect
    data class HandleException(val throwable: Throwable, val retry: () -> Unit) : MyPageEffect
}

data class MyPageState(
    val isLoading: Boolean = false,
    val userName: String = "",
    val canUpdate: Boolean = false,
) : UiState
