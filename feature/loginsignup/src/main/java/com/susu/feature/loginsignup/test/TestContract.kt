package com.susu.feature.loginsignup.test

import com.susu.core.ui.base.SideEffect
import com.susu.core.ui.base.UiState

class TestContract {
    sealed class TestEffect : SideEffect {
        data object NavigateToLogin : TestEffect()
    }

    object TestState : UiState
}
