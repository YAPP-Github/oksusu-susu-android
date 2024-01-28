package com.susu.core.model

import androidx.compose.runtime.Stable

@Stable
data class Relationship(
    val id: Int = -1,
    val relation: String = "",
    val customRelation: String? = null,
    val description: String? = null,
)
