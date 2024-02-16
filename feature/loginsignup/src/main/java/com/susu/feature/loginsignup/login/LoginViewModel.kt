package com.susu.feature.loginsignup.login

import androidx.lifecycle.viewModelScope
import com.susu.core.model.exception.UnknownException
import com.susu.core.ui.SnsProviders
import com.susu.core.ui.base.BaseViewModel
import com.susu.domain.usecase.loginsignup.CheckCanRegisterUseCase
import com.susu.domain.usecase.loginsignup.GetOnboardVoteUseCase
import com.susu.domain.usecase.loginsignup.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val checkCanRegisterUseCase: CheckCanRegisterUseCase,
    private val loginUseCase: LoginUseCase,
    private val getOnboardVoteUseCase: GetOnboardVoteUseCase,
) : BaseViewModel<LoginState, LoginEffect>(LoginState()) {

    fun initData() = viewModelScope.launch {
        getOnboardVoteUseCase().onSuccess {
            intent { copy(onboardVote = it) }
        }.onFailure {
            intent { copy(onboardVote = null) }
        }
    }

    fun login(provider: SnsProviders, oauthAccessToken: String) {
        viewModelScope.launch {
            intent { copy(isLoading = true) }
            // 수수 서버에 가입되지 않은 회원이라면 -> 회원 정보 기입 후 수수 회원가입
            checkCanRegisterUseCase(provider = provider.path, oauthAccessToken = oauthAccessToken)
                .onSuccess { canRegister ->
                    if (canRegister) {
                        postSideEffect(LoginEffect.NavigateToSignUp)
                    } else {
                        loginUseCase(provider = provider.path, oauthAccessToken = oauthAccessToken)
                            .onSuccess {
                                postSideEffect(LoginEffect.NavigateToReceived)
                            }
                            .onFailure {
                                postSideEffect(LoginEffect.ShowSnackBar(message = it.message ?: UnknownException().message))
                            }
                    }
                }.onFailure {
                    postSideEffect(LoginEffect.ShowSnackBar(message = it.message ?: UnknownException().message))
                }
            intent { copy(isLoading = false) }
        }
    }

    fun showSnackBar(message: String) = postSideEffect(LoginEffect.ShowSnackBar(message = message))
}
