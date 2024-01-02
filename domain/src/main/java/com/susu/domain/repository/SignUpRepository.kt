package com.susu.domain.repository

import com.susu.core.model.Token
import com.susu.core.model.User

interface SignUpRepository {
    suspend fun signUp(oauthAccessToken: String, user: User): Token
    suspend fun canRegister(oauthAccessToken: String): Boolean
}
