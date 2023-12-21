package com.susu.feature.loginsignup

import android.content.Context
import com.kakao.sdk.auth.AuthApiClient
import com.kakao.sdk.common.model.AuthError
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Singleton
class KakaoLoginHelper(
    @ApplicationContext private val context: Context,
) {

    fun hasKakaoLoginHistory(): Boolean {
        return AuthApiClient.instance.hasToken()
    }

    fun login(
        onSuccess: (String) -> Unit,
        onFailed: (Throwable?) -> Unit,
    ) {
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            loginWithKakaoTalk(
                context = context,
                onSuccess = onSuccess,
                onFailed = { error ->
                    // 의도적인 사용자의 로그인 취소를 제외한 경우에는 카카오계정 로그인으로 재시도
                    if (error is AuthError || (error is ClientError && error.reason != ClientErrorCause.Cancelled)) {
                        loginWithKakaoAccount(
                            context = context,
                            onSuccess = onSuccess,
                            onFailed = onFailed,
                        )
                    }
                },
            )
        } else { // 카카오톡 미설치 시 카카오계정 로그인 시도
            loginWithKakaoAccount(
                context = context,
                onSuccess = onSuccess,
                onFailed = onFailed,
            )
        }
    }

    private fun loginWithKakaoTalk(
        context: Context,
        onSuccess: (String) -> Unit,
        onFailed: (Throwable?) -> Unit,
    ) {
        UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
            if (token != null) {
                onSuccess(token.accessToken)
            } else {
                onFailed(error)
            }
        }
    }

    private fun loginWithKakaoAccount(
        context: Context,
        onSuccess: (String) -> Unit,
        onFailed: (Throwable?) -> Unit,
    ) {
        UserApiClient.instance.loginWithKakaoAccount(context) { token, error ->
            if (token != null) {
                onSuccess(token.accessToken)
            } else {
                onFailed(error)
            }
        }
    }

    fun logout() = runCatching {
        UserApiClient.instance.logout { error ->
            if (error != null) {
                throw error
            }
        }
    }

    fun unlink() = runCatching {
        UserApiClient.instance.unlink { error ->
            if (error != null) {
                throw error
            }
        }
    }
}
