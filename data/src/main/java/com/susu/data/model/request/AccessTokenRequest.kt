package com.susu.data.model.request

import kotlinx.serialization.Serializable

@Serializable
data class AccessTokenRequest(
    val accessToken: String
)
