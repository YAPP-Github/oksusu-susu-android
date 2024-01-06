package com.susu.data.remote

import com.susu.data.remote.retrofit.SusuErrorResponse
import kotlinx.serialization.json.Json

object Json {
    val instance = Json { encodeDefaults = true }

    fun getSusuErrorBody(body: String) = instance.decodeFromString<SusuErrorResponse>(body)
}
