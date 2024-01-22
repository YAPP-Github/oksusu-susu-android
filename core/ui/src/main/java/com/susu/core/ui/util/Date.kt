package com.susu.core.ui.util

import android.util.Log
import java.time.DateTimeException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

val currentDate: LocalDateTime = LocalDateTime.now()
val minDate: LocalDateTime = LocalDateTime.of(1930, 1, 1, 0, 0)

@Suppress("detekt:FunctionNaming")
fun LocalDateTime.to_yyyy_dot_MM_dot_dd(): String {
    val formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")
    return this.format(formatter)
}

fun getSafeLocalDateTime(year: Int, month: Int, day: Int): LocalDateTime = try {
    LocalDateTime.of(year, month, day, 0, 0)
} catch (e: DateTimeException) {
    Log.e("DateTimeError", "Invalid date provided: $year-$month-$day", e)
    LocalDateTime.of(year, month, 1, 0, 0)
}
