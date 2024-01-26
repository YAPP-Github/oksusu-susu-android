package com.susu.core.model

import java.time.LocalDateTime

data class Vote(
    val id: Long,
    val uid: Long,
    val category: String,
    val content: String,
    val count: Int = 0,
    val isModified: Boolean,
    val createdAt: LocalDateTime,
    val optionList: List<String>,
)
