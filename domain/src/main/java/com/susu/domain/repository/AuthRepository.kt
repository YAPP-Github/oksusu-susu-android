package com.susu.domain.repository

import com.susu.core.model.Token
import com.susu.core.model.User

interface AuthRepository {
    suspend fun login(snsAccessToken: String): Result<Token>
    suspend fun signUp(user: User): Result<Token>
    suspend fun canRegister(snsAccessToken: String): Boolean
    suspend fun logout()
    suspend fun refreshAccessToken(refreshToken: String): Result<Token>
}
