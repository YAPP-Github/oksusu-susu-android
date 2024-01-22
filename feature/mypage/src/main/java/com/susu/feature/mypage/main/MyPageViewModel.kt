package com.susu.feature.mypage.main

import androidx.lifecycle.viewModelScope
import com.kakao.sdk.auth.TokenManagerProvider
import com.kakao.sdk.user.UserApiClient
import com.susu.core.model.exception.UserNotFoundException
import com.susu.core.ui.base.BaseViewModel
import com.susu.domain.usecase.mypage.DownloadExcelUseCase
import com.susu.domain.usecase.mypage.GetUserUseCase
import com.susu.domain.usecase.mypage.LogoutUseCase
import com.susu.domain.usecase.mypage.WithdrawUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase,
    private val withdrawUseCase: WithdrawUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val downloadExcelUseCase: DownloadExcelUseCase,
) : BaseViewModel<MyPageState, MyPageEffect>(MyPageState()) {

    init {
        getUserInfo()
    }

    fun showExportDialog() = postSideEffect(MyPageEffect.ShowExportDialog)
    fun showLogoutDialog() = postSideEffect(MyPageEffect.ShowLogoutDialog)
    fun showWithdrawDialog() = postSideEffect(MyPageEffect.ShowWithdrawDialog)

    private fun getUserInfo() {
        viewModelScope.launch {
            intent { copy(isLoading = true) }
            getUserUseCase().onSuccess {
                intent { copy(userName = it.name) }
            }.onFailure {
                when (it) {
                    is UserNotFoundException -> postSideEffect(MyPageEffect.ShowSnackbar(it.message))
                    else -> postSideEffect(MyPageEffect.HandleException(it, ::getUserInfo))
                }
            }
            intent { copy(isLoading = false) }
        }
    }

    fun logout() {
        UserApiClient.instance.logout { error ->
            if (error != null) {
                postSideEffect(MyPageEffect.HandleException(error, ::logout))
            } else {
                viewModelScope.launch {
                    logoutUseCase().onSuccess {
                        postSideEffect(MyPageEffect.ShowLogoutSuccessSnackbar)
                        postSideEffect(MyPageEffect.NavigateToLogin)
                    }.onFailure {
                        postSideEffect(MyPageEffect.HandleException(it, ::logout))
                    }
                }
            }
        }
    }

    fun withdraw() {
        viewModelScope.launch {
            withdrawUseCase().onSuccess {
                postSideEffect(MyPageEffect.ShowWithdrawSuccessSnackbar)
                postSideEffect(MyPageEffect.NavigateToLogin)
                TokenManagerProvider.instance.manager.clear()
            }.onFailure {
                postSideEffect(MyPageEffect.HandleException(it, ::withdraw))
            }
        }
    }

    fun export() {
        viewModelScope.launch {
            downloadExcelUseCase().onFailure {
                postSideEffect(MyPageEffect.HandleException(it, ::export))
            }
        }
    }
}
