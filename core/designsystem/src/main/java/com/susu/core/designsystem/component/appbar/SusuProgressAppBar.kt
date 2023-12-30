package com.susu.core.designsystem.component.appbar

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.susu.core.designsystem.R
import com.susu.core.designsystem.theme.Orange30
import com.susu.core.designsystem.theme.SusuTheme

@Composable
fun SusuProgressBarAppBar(
    modifier: Modifier = Modifier,
    containerHeight: Dp = 44.dp,
    leftIconType: LeftIconType,
    @DrawableRes leftIcon: Int,
    currentPercentage: Float,
    progressBarColor: Color = SusuTheme.colorScheme.primary,
    progressTrackColor: Color = Orange30,
    progressBarWidth: Dp = 72.dp,
    progressBarHeight: Dp = 4.dp,
    progressBarStrokeCap: StrokeCap = StrokeCap.Round,
    onClickBackButton: () -> Unit = {},
) {
    BasicAppBar(
        containerHeight = containerHeight,
        leftIconType = leftIconType,
        leftIcon = leftIcon,
        title = {
            LinearProgressIndicator(
                progress = {
                    currentPercentage
                },
                color = progressBarColor,
                trackColor = progressTrackColor,
                modifier = modifier
                    .size(
                        width = progressBarWidth,
                        height = progressBarHeight,
                    ),
                strokeCap = progressBarStrokeCap
            )
        },
        onClickBackButton = { onClickBackButton },
    )
}

@Preview(showBackground = true)
@Composable
fun SusuProgressAppBarPreview() {
    val currentPage = 6
    val increasePercent = 1f / currentPage
    var progress by remember { mutableStateOf(increasePercent) }

    val progressAnimation by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing), label = ""
    )

    SusuTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            SusuProgressBarAppBar(
                leftIconType = LeftIconType.BACKBUTTON,
                leftIcon = R.drawable.ic_arrow_left,
                currentPercentage = progressAnimation
            )

            Button(
                onClick = {
                    progress += increasePercent
                }
            ) {
                Text(text = "다음")
            }

            LaunchedEffect(progress) {
                progress = progress
            }
        }
    }
}
