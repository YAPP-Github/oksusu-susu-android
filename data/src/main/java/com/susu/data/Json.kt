package com.susu.data

import com.susu.data.retrofit.SusuErrorResponse
import kotlinx.serialization.json.Json

object Json {
    val instance = Json { encodeDefaults = true }

    fun getSusuErrorBody(body: String) = instance.decodeFromString<SusuErrorResponse>(body)
}
