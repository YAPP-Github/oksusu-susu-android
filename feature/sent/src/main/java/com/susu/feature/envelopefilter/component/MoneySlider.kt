package com.susu.feature.envelopefilter.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.RangeSliderState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.susu.core.designsystem.theme.Gray10
import com.susu.core.designsystem.theme.Gray100
import com.susu.core.designsystem.theme.Gray60
import com.susu.core.designsystem.theme.Gray80
import com.susu.core.designsystem.theme.Orange20
import com.susu.core.designsystem.theme.Orange60
import com.susu.core.designsystem.theme.SusuTheme
import com.susu.core.ui.extension.toMoneyFormat
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoneySlider(
    modifier: Modifier = Modifier,
    height: Dp = 8.dp,
    value: ClosedFloatingPointRange<Float>,
    valueRange: ClosedFloatingPointRange<Float> = 0f..1f,
    onValueChange: (ClosedFloatingPointRange<Float>) -> Unit,
) {
    Box(
        modifier = Modifier.height(24.dp),
        contentAlignment = Alignment.Center,
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(4.dp))
                .fillMaxWidth()
                .height(height)
                .background(Orange20),
        )
        RangeSlider(
            modifier = modifier,
            valueRange = valueRange,
            value = value,
            onValueChange = onValueChange,
            steps = 0,
            startThumb = {
                MoneySliderThumb()
            },
            endThumb = {
                MoneySliderThumb()
            },
            track = {
                MoneySliderTrack(
                    rangeSliderState = it,
                    height = height,
                )
            },
        )
    }
}

@Composable
private fun MoneySliderThumb() {
    Box(
        modifier = Modifier
            .shadow(
                elevation = 8.dp,
                spotColor = Gray60,
                ambientColor = Gray60,
                clip = true,
                shape = CircleShape,
            )
            .size(24.dp)
            .background(color = Gray10, shape = CircleShape)
            .padding(SusuTheme.spacing.spacing_xxxs),
    ) {
        Box(
            modifier = Modifier
                .size(12.dp)
                .background(color = Orange60, shape = CircleShape),
        )
    }
}

@Composable
@ExperimentalMaterial3Api
fun MoneySliderTrack(
    modifier: Modifier = Modifier,
    rangeSliderState: RangeSliderState,
    height: Dp = 8.dp,
) {
    Canvas(
        modifier
            .fillMaxWidth()
            .height(height),
    ) {
        drawTrack(
            activeRangeStart = rangeSliderState.coercedActiveRangeStartAsFraction,
            activeRangeEnd = rangeSliderState.coercedActiveRangeEndAsFraction,
            inactiveTrackColor = Orange20,
            activeTrackColor = Orange60,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
internal val RangeSliderState.coercedActiveRangeStartAsFraction
    get() = calcFraction(
        valueRange.start,
        valueRange.endInclusive,
        activeRangeStart,
    )

@OptIn(ExperimentalMaterial3Api::class)
internal val RangeSliderState.coercedActiveRangeEndAsFraction
    get() = calcFraction(
        valueRange.start,
        valueRange.endInclusive,
        activeRangeEnd,
    )

// Calculate the 0..1 fraction that `pos` value represents between `a` and `b`
private fun calcFraction(a: Float, b: Float, pos: Float) =
    (if (b - a == 0f) 0f else (pos - a) / (b - a)).coerceIn(0f, 1f)

private fun DrawScope.drawTrack(
    activeRangeStart: Float,
    activeRangeEnd: Float,
    inactiveTrackColor: Color,
    activeTrackColor: Color,
) {
    val isRtl = layoutDirection == LayoutDirection.Rtl
    val sliderLeft = Offset(0f, center.y)
    val sliderRight = Offset(size.width, center.y)
    val sliderStart = if (isRtl) sliderRight else sliderLeft
    val sliderEnd = if (isRtl) sliderLeft else sliderRight
    val trackStrokeWidth = 8.dp.toPx()
    drawLine(
        inactiveTrackColor,
        sliderStart,
        sliderEnd,
        trackStrokeWidth,
        StrokeCap.Round,
    )
    val sliderValueEnd = Offset(
        sliderStart.x +
            (sliderEnd.x - sliderStart.x) * activeRangeEnd,
        center.y,
    )

    val sliderValueStart = Offset(
        sliderStart.x +
            (sliderEnd.x - sliderStart.x) * activeRangeStart,
        center.y,
    )

    drawLine(
        activeTrackColor,
        sliderValueStart,
        sliderValueEnd,
        trackStrokeWidth,
        StrokeCap.Round,
    )
}

@Preview(showBackground = true)
@Composable
fun MoneySliderPreview() {
    SusuTheme {
        var value by remember {
            mutableStateOf(0f..3_222f)
        }

        val step = when {
            value.endInclusive <= 10_000 -> 1f
            value.endInclusive <= 10_000 -> 1000f
            value.endInclusive <= 1_000_000 -> 10_000f // 0원 ~ 100만원 범위, 1만원 간격
            value.endInclusive <= 5_000_000 -> 50_000f // 101만원 ~ 500만원 범위, 5만원 간격
            else -> 10_0000f // 500만원 이상, 10만원 간격
        }

        Column(
            modifier = Modifier.padding(10.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(text = "${value.start}")

                Text(text = "${value.endInclusive}")
            }
            MoneySlider(
                value = value,
                valueRange = 0f..3_222f.roundToStep(step) + step,
                onValueChange = { value = it.start.roundToStep(step)..it.endInclusive.roundToStep(step) },
            )
        }
    }
}

fun Float.roundToStep(step: Float): Float {
    return (this / step).roundToInt() * step
}
