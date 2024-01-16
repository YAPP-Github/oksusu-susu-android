package com.susu.domain.repository

import com.susu.core.model.Token

interface LoginRepository {
    suspend fun login(provider: String, oauthAccessToken: String): Token
    suspend fun logout()
    suspend fun withdraw()
}
