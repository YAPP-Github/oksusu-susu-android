package com.susu.feature.navigator.initialization

import com.susu.core.ui.base.SideEffect
import com.susu.core.ui.base.UiState

sealed interface MainContract {
    object MainEffect : SideEffect
    sealed class MainState : UiState {
        data object Loading : MainState()
        data class Initialized(val initialRoute: InitialRoute) : MainState()
    }
}
