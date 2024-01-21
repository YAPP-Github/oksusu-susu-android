package com.susu.core.model

import androidx.compose.runtime.Stable

@Stable
data class RelationShip(
    val id: Int,
    val relation: String,
    val customRelation: String? = null
)
