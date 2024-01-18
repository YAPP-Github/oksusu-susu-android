package com.susu.feature.mypage.main

import com.susu.core.ui.base.SideEffect
import com.susu.core.ui.base.UiState

sealed interface MyPageEffect : SideEffect {
    data object NavigateToLogin : MyPageEffect
    data object NavigateToInfo : MyPageEffect
    data object NavigateToSocial : MyPageEffect
    data class ShowToast(val msg: String) : MyPageEffect
}

data class MyPageState(
    val isLoading: Boolean = false,
    val userName: String = "",
    val appVersion: String = ""
) : UiState
