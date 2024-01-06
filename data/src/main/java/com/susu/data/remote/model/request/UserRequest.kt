package com.susu.data.remote.model.request

import com.susu.core.model.User
import kotlinx.serialization.Serializable

@Serializable
data class UserRequest(
    val name: String,
    val gender: String,
    val birth: Int,
)

fun User.toData() = UserRequest(
    name = name,
    gender = gender,
    birth = birth,
)
