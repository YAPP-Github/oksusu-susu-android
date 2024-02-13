package com.susu.data.remote.model.response

import com.susu.core.model.User
import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    val id: Int,
    val name: String,
    val gender: String = "",
    val birth: Int = 0,
)

fun UserResponse.toModel() = User(
    id = id,
    name = name,
    gender = gender,
    birth = birth,
)
