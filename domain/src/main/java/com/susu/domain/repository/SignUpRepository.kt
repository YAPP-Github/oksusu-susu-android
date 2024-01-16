package com.susu.domain.repository

import com.susu.core.model.Token
import com.susu.core.model.SignUpUser

interface SignUpRepository {
    suspend fun signUp(provider: String, oauthAccessToken: String, signUpUser: SignUpUser): Token
    suspend fun canRegister(provider: String, oauthAccessToken: String): Boolean
}
