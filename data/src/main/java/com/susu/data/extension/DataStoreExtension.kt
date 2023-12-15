package com.susu.data.extension

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.susu.data.Singleton
import com.susu.data.security.SecurityUtil
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

const val SEPARATOR = "|"

suspend inline fun <reified T> DataStore<Preferences>.secureEdit(
    value: T,
    crossinline editStore: (MutablePreferences, String) -> Unit,
) {
    edit {
        val encryptedValue = SecurityUtil.encrypt(Json.encodeToString(value))
        editStore.invoke(it, encryptedValue.joinToString(SEPARATOR))
    }
}

inline fun <reified T> Flow<Preferences>.secureMap(
    crossinline fetchValue: (Preferences) -> String?,
): Flow<T?> {
    return map { pref ->
        fetchValue(pref)?.let { encryptedString ->
            val decryptedValue = SecurityUtil.decrypt(encryptedString.split(SEPARATOR).map { it.toByte() }.toByteArray())
            Singleton.json.decodeFromString(decryptedValue)
        }
    }
}
