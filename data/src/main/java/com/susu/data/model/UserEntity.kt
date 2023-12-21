package com.susu.data.model

import com.susu.core.model.User
import kotlinx.serialization.Serializable

@Serializable
data class UserEntity(
    val name: String,
    val age: Int,
    val birth: Int,
)

fun UserEntity.toDomain() = User(
    name = name,
    age = age,
    birth = birth,
)

fun User.toData() = UserEntity(
    name = name,
    age = age,
    birth = birth,
)
