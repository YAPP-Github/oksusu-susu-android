package com.susu.data.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.susu.core.model.User
import com.susu.data.remote.api.AuthService
import com.susu.data.remote.api.UserService
import com.susu.data.remote.model.request.UserPatchRequest
import com.susu.data.remote.model.response.UserResponse
import com.susu.data.remote.model.response.toModel
import com.susu.domain.repository.UserRepository
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val authService: AuthService,
    private val userService: UserService,
    private val dataStore: DataStore<Preferences>,
    private val json: Json,
) : UserRepository {
    override suspend fun getUserInfo(): User {
        val localUserInfo = dataStore.data.map { preferences ->
            preferences[userKey]
        }.firstOrNull()

        return if (localUserInfo != null) {
            json.decodeFromString<UserResponse>(localUserInfo).toModel()
        } else {
            val remoteUserInfo = userService.getUserInfo().getOrThrow()
            dataStore.edit { preferences ->
                preferences[userKey] = json.encodeToString(remoteUserInfo)
            }
            remoteUserInfo.toModel()
        }
    }

    override suspend fun patchUserInfo(name: String, gender: String, birth: Int) {
        val localUserInfo = dataStore.data.map { preferences ->
            preferences[userKey]
        }.firstOrNull() ?: throw Exception("유저 정보를 불러올 수 없습니다.")

        val uid = json.decodeFromString<UserResponse>(localUserInfo).id
        userService.patchUserInfo(uid = uid, UserPatchRequest(name, gender, birth))
    }

    override suspend fun logout() {
        authService.logout().getOrThrow()
    }

    override suspend fun withdraw() {
        authService.withdraw().getOrThrow()
    }

    companion object {
        private const val userKeyName = "USER"
        private val userKey = stringPreferencesKey(userKeyName)
    }
}
