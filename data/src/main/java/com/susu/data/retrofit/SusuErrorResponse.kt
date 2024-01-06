package com.susu.data.retrofit

import kotlinx.serialization.Serializable

@Serializable
data class SusuErrorResponse(
    val errorCode: String,
    val reason: String,
    val extra: Map<String, String>? = null,
)
