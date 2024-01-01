package com.susu.core.designsystem.component.badge

import androidx.compose.ui.graphics.Color
import com.susu.core.designsystem.theme.Gray10
import com.susu.core.designsystem.theme.Gray70

enum class BadgeColor(
    val backgroundColor: Color,
    val textColor: Color,
) {
    Gray20(
        backgroundColor = com.susu.core.designsystem.theme.Gray20,
        textColor = Gray70,
    ),
    Orange60(
        backgroundColor = com.susu.core.designsystem.theme.Orange60,
        textColor = Gray10,
    ),
    Blue60(
        backgroundColor = com.susu.core.designsystem.theme.Blue60,
        textColor = Gray10,
    ),
    Gray90(
        backgroundColor = com.susu.core.designsystem.theme.Gray90,
        textColor = Gray10,
    ),
    Gray40(
        backgroundColor = com.susu.core.designsystem.theme.Gray40,
        textColor = Gray10,
    ),
    Gray30(
        backgroundColor = com.susu.core.designsystem.theme.Gray30,
        textColor = Gray70,
    ),
    Red60(
        backgroundColor = com.susu.core.designsystem.theme.Red60,
        textColor = Gray10,
    ),
}
