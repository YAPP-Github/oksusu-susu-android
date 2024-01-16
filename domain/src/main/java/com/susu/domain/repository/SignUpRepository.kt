package com.susu.domain.repository

import com.susu.core.model.Token
import com.susu.core.model.User

interface SignUpRepository {
    suspend fun signUp(provider: String, oauthAccessToken: String, user: User): Token
    suspend fun canRegister(provider: String, oauthAccessToken: String): Boolean
}
