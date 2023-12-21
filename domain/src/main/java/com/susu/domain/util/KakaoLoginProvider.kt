package com.susu.domain.util

interface KakaoLoginProvider {
    fun hasKakaoLoginHistory(): Boolean

    fun login(
        onSuccess: (String) -> Unit,
        onFailed: (Throwable?) -> Unit,
    )

    fun loginWithKakaoTalk(
        onSuccess: (String) -> Unit,
        onFailed: (Throwable?) -> Unit,
    )

    fun loginWithKakaoAccount(
        onSuccess: (String) -> Unit,
        onFailed: (Throwable?) -> Unit,
    )

    fun logout(): Result<Unit>
    fun unlink(): Result<Unit>
}
