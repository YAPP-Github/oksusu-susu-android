package com.susu.feature.mypage

import androidx.lifecycle.viewModelScope
import com.kakao.sdk.user.UserApiClient
import com.susu.core.ui.base.BaseViewModel
import com.susu.domain.usecase.LogoutUseCase
import com.susu.domain.usecase.WithdrawUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase,
    private val withdrawUseCase: WithdrawUseCase,
) : BaseViewModel<MyPageContract.MyPageState, MyPageContract.MyPageEffect>(MyPageContract.MyPageState) {

    fun logout() {
        UserApiClient.instance.logout { error ->
            if (error != null) {
                postSideEffect(MyPageContract.MyPageEffect.ShowToast(error.message ?: "에러 발생했지만 토큰은 삭제됨"))
            } else {
                viewModelScope.launch {
                    logoutUseCase().onSuccess {
                        postSideEffect(MyPageContract.MyPageEffect.NavigateToLogin)
                    }
                }
            }
        }
    }

    fun withdraw() {
        UserApiClient.instance.unlink { error ->
            if (error != null) {
                postSideEffect(MyPageContract.MyPageEffect.ShowToast(error.message ?: "에러 발생했지만 토큰은 삭제됨"))
            } else {
                viewModelScope.launch {
                    withdrawUseCase().onSuccess {
                        postSideEffect(MyPageContract.MyPageEffect.NavigateToLogin)
                    }
                }
            }
        }
    }
}
