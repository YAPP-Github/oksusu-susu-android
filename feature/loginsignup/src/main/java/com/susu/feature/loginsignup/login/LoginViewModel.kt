package com.susu.feature.loginsignup.login

import androidx.lifecycle.viewModelScope
import com.kakao.sdk.auth.TokenManagerProvider
import com.susu.core.ui.base.BaseViewModel
import com.susu.domain.repository.AuthRepository
import com.susu.domain.repository.TokenRepository
import com.susu.domain.util.KakaoLoginProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val kakaoLoginProvider: KakaoLoginProvider,
    private val authRepository: AuthRepository,
    private val tokenRepository: TokenRepository,
) : BaseViewModel<LoginContract.LoginState, LoginContract.LoginEffect>(LoginContract.LoginState) {

    fun login() {
        kakaoLoginProvider.login(
            onSuccess = { accessToken ->
                if (TokenManagerProvider.instance.manager.getToken() == null) {
                    // 카카오 로그인 이력이 없을 때만 회원 가입 페이지 이동
                    postSideEffect(LoginContract.LoginEffect.NavigateToSignUp)
                } else {
                    viewModelScope.launch {
                        authRepository.login(accessToken)
                            .onSuccess { token ->
                                tokenRepository.saveTokens(token)
                                postSideEffect(LoginContract.LoginEffect.NavigateToReceived)
                            }.onFailure {
                                // TODO: 음....... 수수 서버 오류?
                            }
                    }
                }
            },
            onFailed = {
                postSideEffect(LoginContract.LoginEffect.ExitProcess(it))
            },
        )
    }
}
