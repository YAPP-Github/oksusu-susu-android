package com.susu.feature.loginsignup

import android.content.Context
import com.kakao.sdk.common.model.AuthError
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import timber.log.Timber

class KakaoLoginHelper(
    private val context: Context,
) {

    fun login(
        onSuccess: (String) -> Unit,
        onFailed: (Throwable?) -> Unit,
    ) {
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            Timber.tag("AUTH").d("카카오톡 설치 확인")
            loginWithKakaoTalk(
                onSuccess = onSuccess,
                onFailed = { error ->
                    // 의도적인 사용자의 로그인 취소를 제외한 경우에는 카카오계정 로그인으로 재시도
                    if (error is AuthError || (error is ClientError && error.reason != ClientErrorCause.Cancelled)) {
                        loginWithKakaoAccount(
                            onSuccess = onSuccess,
                            onFailed = onFailed,
                        )
                    }
                },
            )
        } else { // 카카오톡 미설치 시 카카오계정 로그인 시도
            Timber.tag("AUTH").d("카카오톡 미설치")
            loginWithKakaoAccount(
                onSuccess = onSuccess,
                onFailed = onFailed,
            )
        }
    }

    private fun loginWithKakaoTalk(
        onSuccess: (String) -> Unit,
        onFailed: (Throwable?) -> Unit,
    ) {
        UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
            Timber.tag("AUTH").d("카카오톡 로그인 결과 $token $error")
            if (token != null) {
                onSuccess(token.accessToken)
            } else {
                onFailed(error)
            }
        }
    }

    private fun loginWithKakaoAccount(
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
}
