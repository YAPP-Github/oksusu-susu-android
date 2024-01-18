package com.susu.domain.repository

import com.susu.core.model.User

interface UserRepository {
    suspend fun getUserInfo(): User
    suspend fun patchUserInfo(name: String, gender: String?, birth: Int): User
    suspend fun logout()
    suspend fun withdraw()
}
