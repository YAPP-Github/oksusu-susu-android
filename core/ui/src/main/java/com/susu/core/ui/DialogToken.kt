package com.susu.core.ui

import androidx.compose.ui.text.style.TextAlign

/**
 * @param checkboxText 체크박스 메세지. null 일 경우 SusuDialog, null이 아닐 경우 SusuCheckedDialog 가 노출
 * @param defaultChecked Dialog 노출 시 기본 체크 상태
 * @param onCheckedAction 체크박스 선택 시 추가로 실행하는 로직
 * */
data class DialogToken(
    val title: String? = null,
    val text: String? = null,
    val confirmText: String = "",
    val dismissText: String? = null,
    val checkboxText: String? = null,
    val defaultChecked: Boolean = false,
    val textAlign: TextAlign = TextAlign.Center,
    val onConfirmRequest: () -> Unit = {},
    val onCheckedAction: () -> Unit = {},
    val onDismissRequest: () -> Unit = {},
)
