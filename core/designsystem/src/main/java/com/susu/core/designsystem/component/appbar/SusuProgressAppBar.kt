package com.susu.core.designsystem.component.appbar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.susu.core.designsystem.component.appbar.icon.BackIcon
import com.susu.core.designsystem.theme.SusuTheme

@Composable
fun SusuProgressAppBar(
    modifier: Modifier = Modifier,
    leftIcon: @Composable () -> Unit = {},
    currentStep: Int,
    entireStep: Int,
    progressBar: ProgressBarStyle = ProgressBarStyle.SusuProgressBar,
) {
    BasicAppBar(
        modifier = modifier,
        leftIcon = leftIcon,
        title = {
            LinearProgressIndicator(
                progress = { currentStep / entireStep.toFloat() },
                color = progressBar.progressBarColor,
                trackColor = progressBar.progressBarTrackColor,
                modifier = modifier
                    .size(
                        width = progressBar.progressBarWidth,
                        height = progressBar.progressBarHeight,
                    ),
                strokeCap = progressBar.progressBarStrokeCap,
            )
        },
    )
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun SusuProgressAppBarPreview() {
    val entireStep = 6
    var currentStep by remember { mutableStateOf(1) }

    SusuTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            SusuProgressAppBar(
                leftIcon = {
                    BackIcon()
                },
                currentStep = currentStep,
                entireStep = entireStep,
            )
            Button(
                onClick = {
                    currentStep += 1
                },
            ) {
                Text(text = "다음")
            }
        }
    }
}
