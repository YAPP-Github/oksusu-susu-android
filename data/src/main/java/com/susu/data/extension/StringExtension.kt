package com.susu.data.extension

import org.json.JSONArray
import org.json.JSONObject

fun String.isJsonObject(): Boolean = runCatching { JSONObject(this) }.isSuccess

fun String.isJsonArray(): Boolean = runCatching { JSONArray(this) }.isSuccess
