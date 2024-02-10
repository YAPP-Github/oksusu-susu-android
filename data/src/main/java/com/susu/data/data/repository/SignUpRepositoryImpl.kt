package com.susu.data.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.susu.core.model.SignUpUser
import com.susu.core.model.Token
import com.susu.data.Constants.showOnboardingVoteKey
import com.susu.data.remote.api.SignUpService
import com.susu.data.remote.model.request.toData
import com.susu.data.remote.model.response.toDomain
import com.susu.domain.repository.SignUpRepository
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SignUpRepositoryImpl @Inject constructor(
    private val signUpService: SignUpService,
    private val dataStore: DataStore<Preferences>,
) : SignUpRepository {

    override suspend fun signUp(
        provider: String,
        oauthAccessToken: String,
        signUpUser: SignUpUser,
    ): Token {
        dataStore.edit { preferences ->
            preferences[showOnboardingVoteKey] = false
        }
        return signUpService.signUp(
            provider = provider,
            accessToken = oauthAccessToken,
            user = signUpUser.toData(),
        ).getOrThrow().toDomain()
    }

    override suspend fun canRegister(
        provider: String,
        oauthAccessToken: String,
    ): Boolean {
        return signUpService.checkValidRegister(
            provider = provider,
            accessToken = oauthAccessToken,
        ).getOrThrow().canRegister
    }

    override suspend fun getShowOnboardVote(): Boolean? {
        return dataStore.data.map { preferences ->
            preferences[showOnboardingVoteKey]
        }.firstOrNull()
    }
}
