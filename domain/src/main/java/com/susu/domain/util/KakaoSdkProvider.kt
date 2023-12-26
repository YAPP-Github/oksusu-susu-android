package com.susu.domain.util

interface KakaoSdkProvider {
    fun hasKakaoLoginHistory(): Boolean
    fun getAccessToken(callback: (String?) -> Unit)
    fun logout(): Result<Unit>
    fun unlink(): Result<Unit>
}
