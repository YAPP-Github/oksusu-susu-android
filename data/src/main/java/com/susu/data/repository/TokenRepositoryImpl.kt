package com.susu.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.susu.domain.repository.TokenRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TokenRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) : TokenRepository {

    override fun getAccessToken(): Flow<String?> {
        return dataStore.data.map { preference ->
            preference[ACCESS_TOKEN]
        }
    }

    override fun getRefreshToken(): Flow<String?> {
        return dataStore.data.map { preference ->
            preference[REFRESH_TOKEN]
        }
    }

    override suspend fun saveAccessToken(accessToken: String) {
        dataStore.edit { preference ->
            preference[ACCESS_TOKEN] = accessToken
        }
    }

    override suspend fun saveRefreshToken(refreshToken: String) {
        dataStore.edit { preference ->
            preference[REFRESH_TOKEN] = refreshToken
        }
    }

    override suspend fun deleteAccessToken() {
        dataStore.edit { preference ->
            preference.remove(ACCESS_TOKEN)
        }
    }

    override suspend fun deleteRefreshToken() {
        dataStore.edit { preference ->
            preference.remove(REFRESH_TOKEN)
        }
    }

    override suspend fun refreshAccessToken(): String? {
        TODO("Update Access Token by Refresh Token & return true when sucess")
        TODO("If token refresh failed, make user login again")
    }

    companion object {
        private val ACCESS_TOKEN = stringPreferencesKey("accessToken")
        private val REFRESH_TOKEN = stringPreferencesKey("refreshToken")
    }
}
