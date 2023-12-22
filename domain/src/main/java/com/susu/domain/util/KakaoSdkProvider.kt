package com.susu.domain.util

interface KakaoSdkProvider {
    fun hasKakaoLoginHistory(): Boolean
    fun getAccessToken(): String?
    fun logout(): Result<Unit>
    fun unlink(): Result<Unit>
}
