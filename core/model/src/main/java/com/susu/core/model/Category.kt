package com.susu.core.model

import androidx.compose.runtime.Stable
import kotlinx.serialization.Serializable

@Stable
@Serializable
data class Category(
    val id: Int = 0,
    val seq: Int = 0,
    val name: String = "",
    val customCategory: String? = null,
    val style: String = "",
)
