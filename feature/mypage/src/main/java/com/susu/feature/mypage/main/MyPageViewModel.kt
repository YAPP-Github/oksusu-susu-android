package com.susu.feature.mypage.main

import androidx.lifecycle.viewModelScope
import com.kakao.sdk.user.UserApiClient
import com.susu.core.ui.base.BaseViewModel
import com.susu.domain.usecase.loginsignup.LogoutUseCase
import com.susu.domain.usecase.loginsignup.WithdrawUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase,
    private val withdrawUseCase: WithdrawUseCase,
) : BaseViewModel<MyPageState, MyPageEffect>(MyPageState()) {

    fun logout() {
        UserApiClient.instance.logout { error ->
            if (error != null) {
                postSideEffect(MyPageEffect.ShowToast(error.message ?: "에러 발생했지만 토큰은 삭제됨"))
            } else {
                viewModelScope.launch {
                    logoutUseCase().onSuccess {
                        postSideEffect(MyPageEffect.NavigateToLogin)
                    }
                }
            }
        }
    }

    fun withdraw() {
        UserApiClient.instance.unlink { error ->
            if (error != null) {
                postSideEffect(MyPageEffect.ShowToast(error.message ?: "에러 발생했지만 토큰은 삭제됨"))
            } else {
                viewModelScope.launch {
                    withdrawUseCase().onSuccess {
                        postSideEffect(MyPageEffect.NavigateToLogin)
                    }
                }
            }
        }
    }
}
