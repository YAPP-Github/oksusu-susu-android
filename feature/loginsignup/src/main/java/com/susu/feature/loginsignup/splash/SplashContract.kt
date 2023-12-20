package com.susu.feature.loginsignup.splash

import com.susu.core.ui.base.SideEffect
import com.susu.core.ui.base.UiState

class SplashContract {
    sealed class SplashEffect : SideEffect {
        data object NavigateToSignUpVote : SplashEffect()
        data object NavigateToLogIn : SplashEffect()
        data object NavigateToReceived : SplashEffect()
        data object NavigateToSignUp : SplashEffect()
    }

    data class SplashState(
        val isLoading: Boolean = false,
        val selectedIndex: Int = 0, // 온보딩 투표 선택한 항목
    ) : UiState
}
