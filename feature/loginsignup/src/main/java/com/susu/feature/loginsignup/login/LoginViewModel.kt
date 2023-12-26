package com.susu.feature.loginsignup.login

import androidx.lifecycle.viewModelScope
import com.susu.core.ui.base.BaseViewModel
import com.susu.domain.repository.AuthRepository
import com.susu.domain.repository.TokenRepository
import com.susu.domain.util.KakaoSdkProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val kakaoSdkProvider: KakaoSdkProvider,
    private val authRepository: AuthRepository,
    private val tokenRepository: TokenRepository,
) : BaseViewModel<LoginContract.LoginState, LoginContract.LoginEffect>(LoginContract.LoginState()) {

    init {
        viewModelScope.launch {
            intent { copy(isLoading = true) }
            Timber.tag("AUTH").d("카카오 로그인 이력 확인")
            // 1. 과거 카톡 로그인 이력 확인
            if (!kakaoSdkProvider.hasKakaoLoginHistory()) {
                // 1-1. 신규 유저
                intent { copy(isLoading = false, showVote = true) }
                Timber.tag("AUTH").d("신규유져")
            } else {
                // 2. 카카오 access token 존재 시 로그인 시도
                Timber.d("수수 로그인 시도")
                kakaoSdkProvider.getAccessToken()?.let {
                    authRepository.login(it).onSuccess { token ->
                        Timber.tag("AUTH").d("수수 로그인 성공")
                        runBlocking {
                            tokenRepository.saveTokens(token)
                        }
                        postSideEffect(LoginContract.LoginEffect.NavigateToReceived)
                    }
                    intent { copy(isLoading = false) }
                }
                // 3. 카카오 로그인 필요
            }
        }
    }

    fun login(accessToken: String) {
        viewModelScope.launch {
            intent { copy(isLoading = true) }
            // 수수 서버에 가입되지 않은 회원이라면 -> 회원 정보 기입 후 수수 회원가입
            if (authRepository.canRegister(accessToken)) {
                Timber.tag("AUTH").d("수수 가입 가능")
                postSideEffect(LoginContract.LoginEffect.NavigateToSignUp)
            } else {
                authRepository.login(accessToken).onSuccess { token ->
                    Timber.tag("AUTH").d("수수 로그인 성공")
                    runBlocking {
                        tokenRepository.saveTokens(token)
                    }
                    postSideEffect(LoginContract.LoginEffect.NavigateToReceived)
                }.onFailure {
                    Timber.tag("AUTH").d("수수 로그인 실패 ${it.message}")
                    postSideEffect(LoginContract.LoginEffect.ShowToast(it.message ?: "수수 로그인 실패"))
                }
            }
            intent { copy(isLoading = false) }
        }
    }

    fun selectSignUpVote() {
        intent { copy(showVote = false) }
    }

    fun showToast(error: Throwable?) {
        postSideEffect(LoginContract.LoginEffect.ShowToast(error?.message ?: "에러"))
    }
}
