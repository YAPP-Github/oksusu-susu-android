package com.susu.data.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.susu.core.model.Token
import com.susu.data.Constants
import com.susu.data.remote.api.AuthService
import com.susu.data.remote.model.request.AccessTokenRequest
import com.susu.data.remote.model.response.toDomain
import com.susu.domain.repository.LoginRepository
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val authService: AuthService,
    private val dataStore: DataStore<Preferences>,
) : LoginRepository {
    override suspend fun login(
        provider: String,
        oauthAccessToken: String,
    ): Token {
        dataStore.edit { preferences ->
            preferences[Constants.showOnboardingVoteKey] = false
        }
        return authService.login(
            provider = provider,
            accessTokenRequest = AccessTokenRequest(oauthAccessToken),
        ).getOrThrow().toDomain()
    }
}
