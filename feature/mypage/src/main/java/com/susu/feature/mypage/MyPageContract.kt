package com.susu.feature.mypage

import com.susu.core.ui.base.SideEffect
import com.susu.core.ui.base.UiState

interface MyPageContract {
    sealed class MyPageEffect : SideEffect {
        data object NavigateToLogin : MyPageEffect()
        data class ShowToast(val msg: String) : MyPageEffect()
    }

    object MyPageState : UiState
}
