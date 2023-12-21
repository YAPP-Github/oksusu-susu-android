package com.susu.feature.loginsignup.splash

import androidx.lifecycle.viewModelScope
import com.susu.core.ui.base.BaseViewModel
import com.susu.domain.repository.AuthRepository
import com.susu.domain.repository.TokenRepository
import com.susu.domain.util.KakaoLoginProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val kakaoLoginProvider: KakaoLoginProvider,
    private val tokenRepository: TokenRepository,
    private val authRepository: AuthRepository,
) : BaseViewModel<SplashContract.SplashState, SplashContract.SplashEffect>(SplashContract.SplashState()) {

    init {
        viewModelScope.launch {
            // 1. 과거 카톡 로그인 이력 확인
            if (!kakaoLoginProvider.hasKakaoLoginHistory()) {
                // 1-1. 신규 유저
                postSideEffect(SplashContract.SplashEffect.NavigateToSignUpVote)
            } else {
                // 2. 수수 토큰 여부 확인
                val accessToken = tokenRepository.getAccessToken().firstOrNull()

                if (accessToken == null) {
                    // 2-1. 카카오 로그인 후 이탈한 유저
                    postSideEffect(SplashContract.SplashEffect.NavigateToSignUp)
                } else {
                    // 3. 수수 토큰 만료 여부 확인
                    authRepository.login(accessToken)
                        .onSuccess {
                            // 3-1. 자동 로그인 성공.
                            postSideEffect(SplashContract.SplashEffect.NavigateToReceived)
                        }
                        .onFailure {
                            // 3-2. 수수 토큰 만료됨.
                            postSideEffect(SplashContract.SplashEffect.NavigateToLogIn)
                        }
                }
            }
        }
    }
}
