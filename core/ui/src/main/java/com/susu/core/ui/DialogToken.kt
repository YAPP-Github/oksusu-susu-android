package com.susu.core.ui

import androidx.compose.ui.text.style.TextAlign

data class DialogToken(
    val title: String? = null,
    val text: String? = null,
    val confirmText: String = "",
    val dismissText: String? = null,
    val isDimmed: Boolean = true,
    val textAlign: TextAlign = TextAlign.Center,
    val onConfirmRequest: () -> Unit = {},
    val onDismissRequest: () -> Unit = {},
)
