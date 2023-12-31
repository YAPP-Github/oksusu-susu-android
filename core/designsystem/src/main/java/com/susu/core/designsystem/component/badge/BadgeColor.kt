package com.susu.core.designsystem.component.badge

import androidx.compose.ui.graphics.Color
import com.susu.core.designsystem.theme.Blue60
import com.susu.core.designsystem.theme.Gray10
import com.susu.core.designsystem.theme.Gray20
import com.susu.core.designsystem.theme.Gray30
import com.susu.core.designsystem.theme.Gray40
import com.susu.core.designsystem.theme.Gray70
import com.susu.core.designsystem.theme.Gray90
import com.susu.core.designsystem.theme.Orange60
import com.susu.core.designsystem.theme.Red60

enum class BadgeColor(
    val backgroundColor: Color,
    val textColor: Color,
) {
    BadgeGray20(
        backgroundColor = Gray20,
        textColor = Gray70,
    ),
    BadgeOrange60(
        backgroundColor = Orange60,
        textColor = Gray10,
    ),
    BadgeBlue60(
        backgroundColor = Blue60,
        textColor = Gray10,
    ),
    BadgeGray90(
        backgroundColor = Gray90,
        textColor = Gray10,
    ),
    BadgeGray40(
        backgroundColor = Gray40,
        textColor = Gray10,
    ),
    BadgeGray30(
        backgroundColor = Gray30,
        textColor = Gray70,
    ),
    BadgeRed60(
        backgroundColor = Red60,
        textColor = Gray10,
    ),
}
