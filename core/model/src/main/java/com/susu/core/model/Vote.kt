package com.susu.core.model

import androidx.compose.runtime.Stable
import java.time.LocalDateTime

@Stable
data class Vote(
    val id: Long = 0,
    val uid: Long = 0,
    val category: String = "",
    val content: String = "",
    val count: Int = 0,
    val isModified: Boolean = false,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val optionList: List<VoteOption> = emptyList(),
)

@Stable
data class VoteOption(
    val id: Long,
    val content: String,
)
