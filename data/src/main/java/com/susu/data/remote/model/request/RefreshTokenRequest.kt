package com.susu.data.remote.model.request

import kotlinx.serialization.Serializable

@Serializable
data class RefreshTokenRequest(
    val accessToken: String,
    val refreshToken: String,
)
