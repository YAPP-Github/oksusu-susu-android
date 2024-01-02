package com.susu.feature.loginsignup.test

import androidx.lifecycle.viewModelScope
import com.susu.core.ui.base.BaseViewModel
import com.susu.domain.repository.LoginRepository
import com.susu.domain.repository.SignUpRepository
import com.susu.domain.repository.TokenRepository
import com.susu.feature.loginsignup.social.KakaoLoginHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

// 마이페이지에 들어갈 기능입니다.
@HiltViewModel
class TestViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    private val tokenRepository: TokenRepository,
) : BaseViewModel<TestContract.TestState, TestContract.TestEffect>(TestContract.TestState) {
    fun logout() {
        viewModelScope.launch {
            loginRepository.logout()
            KakaoLoginHelper.logout()
            tokenRepository.deleteTokens()
        }
        postSideEffect(TestContract.TestEffect.NavigateToLogin)
    }

    fun withdraw() {
        KakaoLoginHelper.unlink().onSuccess {
            viewModelScope.launch {
                runBlocking { loginRepository.withdraw() }
                tokenRepository.deleteTokens()
            }
        }
        postSideEffect(TestContract.TestEffect.NavigateToLogin)
    }
}
