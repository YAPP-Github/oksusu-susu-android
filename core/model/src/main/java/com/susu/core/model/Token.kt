package com.susu.core.model

data class Token(
    val accessToken: String,
    val accessTokenExp: String,
    val refreshToken: String,
    val refreshTokenExp: String,
)
