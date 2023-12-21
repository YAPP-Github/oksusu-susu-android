package com.susu.data.model

import com.susu.core.model.Token
import kotlinx.serialization.Serializable

@Serializable
data class TokenEntity(
    val accessToken: String,
    val accessTokenExp: String,
    val refreshToken: String,
    val refreshTokenExp: String,
)

fun TokenEntity.toDomain() = Token(
    accessToken = accessToken,
    accessTokenExp = accessTokenExp,
    refreshToken = refreshToken,
    refreshTokenExp = refreshTokenExp,
)

fun Token.toData() = TokenEntity(
    accessToken = accessToken,
    accessTokenExp = accessTokenExp,
    refreshToken = refreshToken,
    refreshTokenExp = refreshTokenExp,
)
