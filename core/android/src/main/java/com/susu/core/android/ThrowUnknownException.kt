package com.susu.core.android

import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase

fun throwUnknownException(
    e: Throwable,
) {
    Firebase.crashlytics.recordException(e)
    throw e
}
