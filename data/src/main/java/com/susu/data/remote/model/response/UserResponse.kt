package com.susu.data.remote.model.response

import com.susu.core.model.User
import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    val id: Int,
    val name: String,
    val gender: String,
    val birth: String,
)

fun User.toData() = UserResponse(
    id = id,
    name = name,
    gender = gender,
    birth = birth.toString(),
)

fun UserResponse.toModel() = User(
    id = id,
    name = name,
    gender = gender,
    birth = 0, // TODO: api 형식 변경 예정
)
