package com.susu.data.extension

import org.json.JSONArray
import org.json.JSONObject

fun String.isJsonObject(): Boolean = try {
    JSONObject(this)
    true
} catch (e: Exception) {
    false
}

fun String.isJsonArray(): Boolean = try {
    JSONArray(this)
    true
} catch (e: Exception) {
    false
}
