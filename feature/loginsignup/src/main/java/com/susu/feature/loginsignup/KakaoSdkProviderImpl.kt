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

    override fun getAccessToken(
        callback: (String?) -> Unit,
    ) {
        UserApiClient.instance.accessTokenInfo { _, error ->
            if (error != null) {
                callback(null)
            } else {
                // 토큰 유효성 체크 성공(필요 시 토큰 갱신됨)
                callback(TokenManagerProvider.instance.manager.getToken()?.accessToken)
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
