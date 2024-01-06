package com.susu.data.data.repository

import com.susu.core.model.Token
import com.susu.data.security.SecurityPreferences
import com.susu.domain.repository.TokenRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TokenRepositoryImpl @Inject constructor(
    private val securityPreferences: SecurityPreferences,
) : TokenRepository {

    override fun getAccessToken(): Flow<String> {
        return securityPreferences.flowAccessToken()
    }

    override fun getRefreshToken(): Flow<String> {
        return securityPreferences.flowRefreshToken()
    }

    override suspend fun saveTokens(token: Token) {
        securityPreferences.run {
            setAccessToken(token.accessToken)
            setRefreshToken(token.refreshToken)
        }
    }

    override suspend fun deleteTokens() {
        securityPreferences.clearAll()
    }
}
