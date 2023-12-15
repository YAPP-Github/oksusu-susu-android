package com.susu.data.extension

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

fun String.isJsonObject(): Boolean = try {
    JSONObject(this)
    true
} catch (e: JSONException) {
    false
}

fun String.isJsonArray(): Boolean = try {
    JSONArray(this)
    true
} catch (e: JSONException) {
    false
}
