package com.susu.feature.loginsignup.login

import androidx.lifecycle.viewModelScope
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
) : BaseViewModel<LoginContract.LoginState, LoginContract.LoginEffect>(LoginContract.LoginState()) {

    init {
        viewModelScope.launch {
            // 1. 과거 카톡 로그인 이력 확인
            if (!kakaoLoginProvider.hasKakaoLoginHistory()) {
                // 1-1. 신규 유저
                intent { copy(showVote = true) }
            } else {
                // 2. 카카오 access token 존재 시 로그인 시도
                kakaoLoginProvider.getAccessToken()?.let {
                    authRepository.login(it).onSuccess {
                        postSideEffect(LoginContract.LoginEffect.NavigateToReceived)
                    }
                }
                // 3. 카카오 로그인 필요
            }
        }
    }

    fun login() {
        kakaoLoginProvider.login(
            onSuccess = { accessToken ->
                viewModelScope.launch {
                    // 수수 서버에 가입되지 않은 회원이라면 -> 회원 정보 기입 후 수수 회원가입
                    if (authRepository.canRegister(accessToken)) {
                        postSideEffect(LoginContract.LoginEffect.NavigateToSignUp)
                    } else {
                        authRepository.login(accessToken).onSuccess { token ->
                            tokenRepository.saveTokens(token)
                            postSideEffect(LoginContract.LoginEffect.NavigateToReceived)
                        }.onFailure {
                            postSideEffect(LoginContract.LoginEffect.ExitProcess(it))
                        }
                    }
                }
            },
            onFailed = {
                postSideEffect(LoginContract.LoginEffect.ExitProcess(it))
            },
        )
    }

    fun selectSignUpVote() {
        intent { copy(showVote = false) }
    }
}
