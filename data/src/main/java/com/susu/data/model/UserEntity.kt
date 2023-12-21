package com.susu.data.model

import com.susu.core.model.User
import kotlinx.serialization.Serializable

@Serializable
data class UserEntity(
    val name: String,
    val gender: String,
    val birth: Int,
)

fun UserEntity.toDomain() = User(
    name = name,
    gender = gender,
    birth = birth,
)

fun User.toData() = UserEntity(
    name = name,
    gender = gender,
    birth = birth,
)
