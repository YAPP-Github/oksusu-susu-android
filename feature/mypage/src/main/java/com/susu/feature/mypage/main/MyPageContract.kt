package com.susu.feature.mypage.main

import com.susu.core.ui.base.SideEffect
import com.susu.core.ui.base.UiState

sealed interface MyPageEffect : SideEffect {
    data object NavigateToLogin : MyPageEffect
    data class ShowToast(val msg: String) : MyPageEffect
}

object MyPageState : UiState
