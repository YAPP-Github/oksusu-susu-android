package com.susu.feature.navigator.initialization

import com.susu.core.ui.base.SideEffect
import com.susu.core.ui.base.UiState

sealed interface MainContract {
    object MainEffect : SideEffect
    data class MainState(
        val isLoading: Boolean = true,
        val initialRoute: InitialRoute = InitialRoute.NONE,
    ) : UiState
}
