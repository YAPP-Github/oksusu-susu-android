package com.susu.domain.repository

import com.susu.core.model.Token
import kotlinx.coroutines.flow.Flow

interface TokenRepository {
    fun getAccessToken(): Flow<String?>
    fun getRefreshToken(): Flow<String?>
    suspend fun saveTokens(token: Token)
    suspend fun deleteTokens()
}
