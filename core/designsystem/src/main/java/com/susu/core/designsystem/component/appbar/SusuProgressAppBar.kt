package com.susu.core.designsystem.component.appbar

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.susu.core.designsystem.R
import com.susu.core.designsystem.theme.SusuTheme
import com.susu.core.ui.extension.susuClickable

@Composable
fun SusuProgressAppBar(
    modifier: Modifier = Modifier,
    @DrawableRes leftIcon: Int,
    leftIconContentDescription: String,
    leftIconPadding: Dp = SusuTheme.spacing.spacing_xs,
    currentStep: Int,
    entireStep: Int,
    progressBar: ProgressBarStyle = ProgressBarStyle.SusuProgressBar,
    onClickBackButton: () -> Unit,
) {
    BasicAppBar(
        modifier = modifier,
        leftIcon = {
            Box(
                modifier = modifier
                    .susuClickable(
                        rippleEnabled = false,
                        onClick = onClickBackButton,
                    )
                    .padding(leftIconPadding),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    painter = painterResource(id = leftIcon),
                    contentDescription = leftIconContentDescription,
                )
            }
        },
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
fun SusuProgressAppBarPreview(
    modifier: Modifier = Modifier,
) {
    val entireStep = 6
    var currentStep by remember { mutableStateOf(1) }

    SusuTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            SusuProgressAppBar(
                leftIcon = R.drawable.ic_arrow_left,
                leftIconContentDescription = "뒤로가기",
                currentStep = currentStep,
                entireStep = entireStep,
                onClickBackButton = {
                    Log.d("확인", "왼쪽 뒤로가기 클릭")
                },
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
