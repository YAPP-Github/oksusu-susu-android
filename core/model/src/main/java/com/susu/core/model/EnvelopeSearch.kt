package com.susu.core.model

data class EnvelopeSearch(
    val envelope: Envelope,
    val category: Category? = null,
    val friend: Friend? = null,
    val relationship: Relationship? = null,
)
