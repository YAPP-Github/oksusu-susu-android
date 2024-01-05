package com.susu.feature.navigator.initialization

import androidx.lifecycle.viewModelScope
import com.susu.core.ui.base.BaseViewModel
import com.susu.domain.usecase.CheckCanRegisterUseCase
import com.susu.domain.usecase.LoginUseCase
import com.susu.feature.loginsignup.social.KakaoLoginHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val checkCanRegisterUseCase: CheckCanRegisterUseCase,
) : BaseViewModel<MainContract.MainState, MainContract.MainEffect>(MainContract.MainState()) {
    init {
        if (!KakaoLoginHelper.hasKakaoLoginHistory()) {
            intent { copy(isLoading = false, initialRoute = InitialRoute.SIGNUP_VOTE) }
        } else {
            KakaoLoginHelper.getAccessToken { oauthAccessToken ->
                if (oauthAccessToken == null) {
                    // 웹에서 회원 탈퇴를 하고 앱에 접속했을 때 -> 카카오 로그인 화면으로 이동
                    intent { copy(isLoading = false, initialRoute = InitialRoute.LOGIN) }
                } else {
                    viewModelScope.launch {
                        checkCanRegisterUseCase(oauthAccessToken).onSuccess { canRegister ->
                            if (canRegister) {
                                intent { copy(isLoading = false, initialRoute = InitialRoute.SIGNUP) }
                            } else {
                                login(oauthAccessToken)
                            }
                        }
                    }
                }
            }
        }
    }

    private suspend fun login(oauthAccessToken: String) {
        loginUseCase(oauthAccessToken = oauthAccessToken)
            .onSuccess {
                intent { copy(isLoading = false, initialRoute = InitialRoute.SENT) }
            }.onFailure {
                intent { copy(isLoading = false, initialRoute = InitialRoute.LOGIN) }
            }
    }
}
