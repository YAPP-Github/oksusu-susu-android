package com.susu.core.ui.util

import java.time.DateTimeException
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Month
import java.time.YearMonth
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
    LocalDateTime.of(year, month, 1, 0, 0)
}
