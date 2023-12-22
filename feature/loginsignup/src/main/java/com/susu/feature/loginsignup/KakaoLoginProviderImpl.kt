package com.susu.feature.loginsignup

import android.content.Context
import com.kakao.sdk.auth.AuthApiClient
import com.kakao.sdk.auth.TokenManagerProvider
import com.kakao.sdk.common.model.AuthError
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.susu.domain.util.KakaoLoginProvider
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class KakaoLoginProviderImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : KakaoLoginProvider {

    override fun hasKakaoLoginHistory(): Boolean {
        return AuthApiClient.instance.hasToken()
    }

    override fun getAccessToken(): String? {
        return TokenManagerProvider.instance.manager.getToken()?.accessToken
    }

    override fun login(
        onSuccess: (String) -> Unit,
        onFailed: (Throwable?) -> Unit,
    ) {
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
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
            loginWithKakaoAccount(
                onSuccess = onSuccess,
                onFailed = onFailed,
            )
        }
    }

    override fun loginWithKakaoTalk(
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

    override fun loginWithKakaoAccount(
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

    override fun logout() = runCatching {
        UserApiClient.instance.logout { error ->
            if (error != null) {
                throw error
            }
        }
    }

    override fun unlink() = runCatching {
        UserApiClient.instance.unlink { error ->
            if (error != null) {
                throw error
            }
        }
    }
}
