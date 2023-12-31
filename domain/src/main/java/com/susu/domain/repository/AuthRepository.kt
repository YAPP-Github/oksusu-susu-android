package com.susu.domain.repository

import com.susu.core.model.Token
import com.susu.core.model.User

interface AuthRepository {
    suspend fun login(accessToken: String): Result<Token>
    suspend fun signUp(oauthAccessToken: String, user: User): Result<Token>
    suspend fun canRegister(accessToken: String): Boolean
    suspend fun logout()
    suspend fun withdraw()
}
