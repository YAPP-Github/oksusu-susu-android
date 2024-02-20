package com.susu.core.ui.extension

fun String.toPhoneNumber(): String = runCatching {
    "${this.substring(0, 3)}-${this.substring(3, 7)}-${this.substring(7)}"
}.getOrNull() ?: this
