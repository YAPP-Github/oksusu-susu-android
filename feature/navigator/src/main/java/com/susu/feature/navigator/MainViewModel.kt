package com.susu.feature.navigator

import androidx.lifecycle.viewModelScope
import com.susu.core.ui.base.BaseViewModel
import com.susu.domain.usecase.loginsignup.CheckCanRegisterUseCase
import com.susu.domain.usecase.loginsignup.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val checkCanRegisterUseCase: CheckCanRegisterUseCase,
) : BaseViewModel<MainState, MainSideEffect>(MainState()) {
    companion object {
        const val NAVIGATE_DELAY = 500L
    }

    fun navigate(hasKakaoLoginHistory: Boolean, kakaoAccessToken: String?) = viewModelScope.launch {
        if (hasKakaoLoginHistory.not()) {
            intent { copy(isNavigating = false) }
            return@launch
        }

        if (kakaoAccessToken == null) {
            postSideEffect(MainSideEffect.NavigateLogin)
            intent { copy(isNavigating = false) }
            return@launch
        }

        checkCanRegisterUseCase(kakaoAccessToken)
            .onSuccess { canRegister ->
                handleCanRegisterSuccess(
                    canRegister = canRegister,
                    kakaoAccessToken = kakaoAccessToken,
                )
            }
        delay(NAVIGATE_DELAY)
        intent { copy(isNavigating = false) }
    }

    private suspend fun handleCanRegisterSuccess(canRegister: Boolean, kakaoAccessToken: String) {
        if (canRegister) postSideEffect(MainSideEffect.NavigateSignup)
        else login(kakaoAccessToken)
    }

    private suspend fun login(oauthAccessToken: String) {
        loginUseCase(oauthAccessToken = oauthAccessToken)
            .onSuccess {
                postSideEffect(MainSideEffect.NavigateSent)
            }.onFailure {
                postSideEffect(MainSideEffect.NavigateLogin)
            }
    }
}
