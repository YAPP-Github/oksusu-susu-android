package com.susu.core.model

import androidx.compose.runtime.Stable
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toKotlinLocalDateTime
import kotlinx.serialization.Serializable

@Stable
@Serializable
data class Vote(
    val id: Long = 0,
    val uid: Long = 0,
    val profile: Profile = Profile(),
    val boardId: Long = 0,
    val boardName: String = "",
    val content: String = "",
    val count: Long = 0,
    val isModified: Boolean = false,
    val isMine: Boolean = false,
    val createdAt: LocalDateTime = java.time.LocalDateTime.now().toKotlinLocalDateTime(),
    val optionList: List<VoteOption> = emptyList(),
)

@Stable
@Serializable
data class Profile(
    val id: Long = 0,
    val name: String = "",
)

@Stable
@Serializable
data class VoteOption(
    val id: Long,
    val content: String,
    val count: Long = 0,
    val isVoted: Boolean = false,
)
