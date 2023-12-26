package com.susu.data.model.response

import kotlinx.serialization.Serializable

@Serializable
data class ValidRegisterResponse(
    val canRegister: Boolean,
)
