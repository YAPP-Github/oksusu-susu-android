package com.susu.core.ui.extension

fun String.toPhoneNumber(): String {
    return "${this.substring(0, 3)}-${this.substring(3, 7)}-${this.substring(7)}"
}
