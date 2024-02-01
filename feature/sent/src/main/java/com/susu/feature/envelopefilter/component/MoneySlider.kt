package com.susu.feature.envelopefilter.component

import androidx.annotation.IntRange
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.RangeSliderState
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
import androidx.compose.ui.geometry.lerp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.susu.core.designsystem.theme.Gray10
import com.susu.core.designsystem.theme.Orange20
import com.susu.core.designsystem.theme.Orange60
import com.susu.core.designsystem.theme.SusuTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoneySlider(
    modifier: Modifier = Modifier,
    height: Dp = 8.dp,
    value: ClosedFloatingPointRange<Float>,
    valueRange: ClosedFloatingPointRange<Float> = 0f..1f,
    onValueChange: (ClosedFloatingPointRange<Float>) -> Unit,
    @IntRange(from = 0)
    steps: Int = 0
) {
    Box(
        contentAlignment = Alignment.Center
    ) {
        Box(modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .fillMaxWidth()
            .height(height)
            .background(Orange20)
        )
        RangeSlider(
            modifier = modifier,
            valueRange = valueRange,
            value = value,
            onValueChange = onValueChange,
            steps = steps,
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
            }
        )
    }

}

@Composable
private fun MoneySliderThumb() {
    Box(
        modifier = Modifier
            .shadow(elevation = 8.dp, spotColor = Color(0x14000000), ambientColor = Color(0x14000000), clip = true, shape = CircleShape)
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
            .height(height)
    ) {
        drawTrack(
            activeRangeStart = rangeSliderState.coercedActiveRangeStartAsFraction,
            activeRangeEnd = rangeSliderState.coercedActiveRangeEndAsFraction,
            inactiveTrackColor = Orange20,
            activeTrackColor = Orange60
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
internal val RangeSliderState.coercedActiveRangeStartAsFraction
    get() = calcFraction(
        valueRange.start,
        valueRange.endInclusive,
        activeRangeStart
    )

@OptIn(ExperimentalMaterial3Api::class)
internal val RangeSliderState.coercedActiveRangeEndAsFraction
    get() = calcFraction(
        valueRange.start,
        valueRange.endInclusive,
        activeRangeEnd
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
        StrokeCap.Round
    )
    val sliderValueEnd = Offset(
        sliderStart.x +
            (sliderEnd.x - sliderStart.x) * activeRangeEnd,
        center.y
    )

    val sliderValueStart = Offset(
        sliderStart.x +
            (sliderEnd.x - sliderStart.x) * activeRangeStart,
        center.y
    )

    drawLine(
        activeTrackColor,
        sliderValueStart,
        sliderValueEnd,
        trackStrokeWidth,
        StrokeCap.Round
    )
}

@Preview(showBackground = true)
@Composable
fun MoneySliderPreview() {
    var value by remember {
        mutableStateOf(10f .. 10f)
    }

    SusuTheme {
        Column(
            modifier = Modifier.padding(10.dp),
        ) {
            MoneySlider(value = value, valueRange = 10f .. 10f, onValueChange = { value = it }, steps = 10)
        }
    }
}
