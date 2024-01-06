package com.susu.data.remote.retrofit

import kotlinx.serialization.Serializable

@Serializable
data class SusuErrorResponse(
    val errorCode: String,
    val reason: String,
    val extra: Map<String, String>? = null,
)
