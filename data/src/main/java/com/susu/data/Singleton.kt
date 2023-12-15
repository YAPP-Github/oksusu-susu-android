package com.susu.data

import kotlinx.serialization.json.Json

object Singleton {
    val json = Json { encodeDefaults = true }
}
