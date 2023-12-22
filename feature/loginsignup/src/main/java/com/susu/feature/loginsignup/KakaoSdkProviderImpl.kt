package com.susu.feature.loginsignup

import com.kakao.sdk.auth.AuthApiClient
import com.kakao.sdk.auth.TokenManagerProvider
import com.kakao.sdk.user.UserApiClient
import com.susu.domain.util.KakaoSdkProvider
import javax.inject.Inject

class KakaoSdkProviderImpl @Inject constructor() : KakaoSdkProvider {

    override fun hasKakaoLoginHistory(): Boolean {
        return AuthApiClient.instance.hasToken()
    }

    override fun getAccessToken(): String? {
        return TokenManagerProvider.instance.manager.getToken()?.accessToken
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
