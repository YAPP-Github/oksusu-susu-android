package com.susu.data.remote.model.response

import kotlinx.serialization.Serializable

@Serializable
data class ValidRegisterResponse(
    val canRegister: Boolean,
)
