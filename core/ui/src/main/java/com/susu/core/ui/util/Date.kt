package com.susu.core.ui.util

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

val currentDate = LocalDateTime.now()

@Suppress("detekt:FunctionNaming")
fun LocalDateTime.to_yyyy_dot_MM_dot_dd(): String {
    val formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")
    return this.format(formatter)
}
