package com.susu.feature.navigator.initialization

import androidx.lifecycle.viewModelScope
import com.susu.core.ui.base.BaseViewModel
import com.susu.domain.usecase.LoginUseCase
import com.susu.feature.loginsignup.social.KakaoLoginHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
) : BaseViewModel<MainContract.MainState, MainContract.MainEffect>(MainContract.MainState()) {
    init {
        if (!KakaoLoginHelper.hasKakaoLoginHistory()) {
            intent { copy(isLoading = false, initialRoute = InitialRoute.SIGNUP_VOTE) }
        } else {
            KakaoLoginHelper.getAccessToken { oauthAccessToken ->
                if (oauthAccessToken == null) {
                    intent { copy(isLoading = false, initialRoute = InitialRoute.LOGIN) }
                } else {
                    viewModelScope.launch {
                        loginUseCase(oauthAccessToken = oauthAccessToken)
                            .onSuccess {
                                intent { copy(isLoading = false, initialRoute = InitialRoute.RECEIVED) }
                            }.onFailure {
                                intent { copy(isLoading = false, initialRoute = InitialRoute.LOGIN) }
                            }
                    }
                }
            }
        }
    }
}
