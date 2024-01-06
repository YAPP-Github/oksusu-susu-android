package com.susu.data.model.request

import kotlinx.serialization.Serializable

@Serializable
data class RefreshTokenRequest(
    val accessToken: String,
    val refreshToken: String,
)
