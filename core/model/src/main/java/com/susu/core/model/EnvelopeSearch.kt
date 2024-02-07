package com.susu.core.model

import androidx.compose.runtime.Stable

@Stable
data class EnvelopeSearch(
    val envelope: Envelope,
    val category: Category? = null,
    val friend: Friend? = null,
    val relationship: Relationship? = null,
)
