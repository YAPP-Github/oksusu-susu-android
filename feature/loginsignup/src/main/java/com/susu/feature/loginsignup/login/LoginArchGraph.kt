package com.susu.feature.loginsignup.login

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.EaseInOutCubic
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.susu.core.designsystem.theme.Orange20
import com.susu.core.designsystem.theme.Orange50
import com.susu.core.designsystem.theme.SusuTheme
import kotlinx.coroutines.launch

@Composable
fun LoginArchGraph(
    modifier: Modifier = Modifier,
    outlineWidth: Float = 16f,
    fillFrom: Float = 0f,
    fillUntil: Float = 0.87f,
) {
    val fillAngle = remember { Animatable(fillFrom) }

    LaunchedEffect(key1 = fillAngle) {
        launch {
            fillAngle.animateTo(
                targetValue = fillUntil,
                animationSpec = tween(
                    delayMillis = 300,
                    durationMillis = 1000,
                    easing = EaseInOutCubic,
                ),
            )
        }
    }

    Canvas(
        modifier = modifier,
        onDraw = {
            drawArc(
                color = Orange50,
                startAngle = 0f,
                sweepAngle = -1f * 360f * fillAngle.value,
                useCenter = true,
            )
            drawCircle(
                color = Orange20,
                alpha = 0.48f,
                radius = size.minDimension / 2.0f - outlineWidth,
            )
        },
    )
}

@Preview(showBackground = true)
@Composable
fun LoginArchGraphPreview() {
    SusuTheme {
        LoginArchGraph(modifier = Modifier.size(200.dp))
    }
}
