package com.susu.data.model.response

import com.susu.core.model.Token
import kotlinx.serialization.Serializable

@Serializable
data class TokenResponse(
    val accessToken: String,
    val refreshToken: String,
)

fun TokenResponse.toDomain() = Token(
    accessToken = accessToken,
    refreshToken = refreshToken,
)
