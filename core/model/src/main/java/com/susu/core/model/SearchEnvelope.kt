package com.susu.core.model

data class SearchEnvelope(
    val envelope: Envelope = Envelope(),
    val category: Category = Category(),
    val friend: Friend = Friend(),
    val relation: Relationship = Relationship(),
)
