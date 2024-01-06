package com.susu.feature.loginsignup.login

import androidx.lifecycle.viewModelScope
import com.susu.core.ui.base.BaseViewModel
import com.susu.domain.usecase.CheckCanRegisterUseCase
import com.susu.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val checkCanRegisterUseCase: CheckCanRegisterUseCase,
    private val loginUseCase: LoginUseCase,
) : BaseViewModel<LoginContract.LoginState, LoginContract.LoginEffect>(LoginContract.LoginState()) {

    fun login(oauthAccessToken: String) {
        viewModelScope.launch {
            intent { copy(isLoading = true) }
            // 수수 서버에 가입되지 않은 회원이라면 -> 회원 정보 기입 후 수수 회원가입
            checkCanRegisterUseCase(oauthAccessToken = oauthAccessToken).onSuccess { canRegister ->
                if (canRegister) {
                    postSideEffect(LoginContract.LoginEffect.NavigateToSignUp)
                } else {
                    loginUseCase(oauthAccessToken = oauthAccessToken)
                        .onSuccess {
                            postSideEffect(LoginContract.LoginEffect.NavigateToReceived)
                        }
                        .onFailure {
                            postSideEffect(LoginContract.LoginEffect.ShowToast(it.message ?: "수수 로그인 실패"))
                        }
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
