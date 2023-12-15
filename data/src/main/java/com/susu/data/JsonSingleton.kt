package com.susu.data

import kotlinx.serialization.json.Json

object JsonSingleton {
    val instance = Json { encodeDefaults = true }
}
