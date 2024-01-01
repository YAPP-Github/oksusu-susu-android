package com.susu.core.designsystem.component.appbar

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.susu.core.designsystem.theme.Orange30
import com.susu.core.designsystem.theme.Orange60

enum class ProgressBarStyle(
    val progressBarColor: Color,
    val progressBarTrackColor: Color,
    val progressBarWidth: Dp,
    val progressBarHeight: Dp,
    val progressBarStrokeCap: StrokeCap,
) {
    SusuProgressBar(
        progressBarColor = Orange60,
        progressBarTrackColor = Orange30,
        progressBarWidth = 72.dp,
        progressBarHeight = 4.dp,
        progressBarStrokeCap = StrokeCap.Round,
    )
}
