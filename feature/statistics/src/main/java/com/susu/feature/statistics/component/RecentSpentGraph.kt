package com.susu.feature.statistics.component

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.EaseInOutCubic
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.susu.core.designsystem.theme.Blue60
import com.susu.core.designsystem.theme.Gray10
import com.susu.core.designsystem.theme.Gray100
import com.susu.core.designsystem.theme.Gray30
import com.susu.core.designsystem.theme.Gray40
import com.susu.core.designsystem.theme.Gray60
import com.susu.core.designsystem.theme.Gray90
import com.susu.core.designsystem.theme.Orange30
import com.susu.core.designsystem.theme.Orange60
import com.susu.core.designsystem.theme.SusuTheme
import com.susu.feature.statistics.R
import kotlin.random.Random

@Composable
fun RecentSpentGraph(
    modifier: Modifier = Modifier,
    isActive: Boolean = true,
    spentData: List<Pair<String, Int>> = emptyList(),
) {
    val totalAmount by remember { mutableStateOf(spentData.sumOf { it.second } / 10000) }
    val maximumAmount by remember { mutableStateOf(spentData.maxOf { it.second }) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(color = Gray10, shape = RoundedCornerShape(4.dp))
            .padding(SusuTheme.spacing.spacing_xxs),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(SusuTheme.spacing.spacing_xxs),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = stringResource(R.string.statistics_recent_8_total_money),
                style = SusuTheme.typography.title_xs,
                color = Gray100,
            )
            if (isActive) {
                Text(
                    text = stringResource(R.string.statistics_total_man_format, totalAmount.toString()),
                    style = SusuTheme.typography.title_xs,
                    color = Blue60,
                )
            } else {
                Text(
                    text = stringResource(R.string.statistics_total_man_format, stringResource(R.string.word_unkown)),
                    style = SusuTheme.typography.title_xs,
                    color = Gray40,
                )
            }
        }
        Spacer(modifier = Modifier.height(SusuTheme.spacing.spacing_xxs))
        Row(
            modifier = if (isActive) {
                Modifier.fillMaxWidth().padding(SusuTheme.spacing.spacing_xxs)
            } else {
                Modifier.fillMaxWidth().blur(8.dp).padding(SusuTheme.spacing.spacing_xxs)
            },
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            spentData.forEachIndexed { i, data ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    StickGraph(
                        ratio = data.second.toFloat() / maximumAmount,
                        color = if (isActive) {
                            if (i == spentData.lastIndex) {
                                Orange60
                            } else {
                                Orange30
                            }
                        } else {
                            if (i == spentData.lastIndex) {
                                Gray60
                            } else {
                                Gray30
                            }
                        },
                    )
                    Spacer(modifier = Modifier.height(SusuTheme.spacing.spacing_xxxxs))
                    Text(
                        text = data.first,
                        style = SusuTheme.typography.title_xxxs,
                        color = if (i == spentData.lastIndex) {
                            Gray90
                        } else {
                            Gray40
                        },
                    )
                }
                Spacer(modifier = Modifier.width(SusuTheme.spacing.spacing_s))
            }
        }
    }
}

@Composable
fun StickGraph(
    ratio: Float,
    color: Color,
    modifier: Modifier = Modifier,
    width: Dp = 24.dp,
    maximumHeight: Dp = 80.dp,
    delay: Int = 0,
    duration: Int = 800,
) {
    var initialHeight by remember { mutableStateOf(0f) }
    val fillHeight = remember { Animatable(initialHeight) }
    val graphHeight = with(LocalDensity.current) { maximumHeight.toPx() * ratio }

    LaunchedEffect(key1 = ratio) {
        fillHeight.animateTo(
            targetValue = graphHeight,
            animationSpec = tween(
                delayMillis = delay,
                durationMillis = duration,
                easing = EaseInOutCubic,
            ),
        )
        initialHeight = ratio
    }

    Spacer(
        modifier = modifier
            .size(width = width, height = maximumHeight)
            .drawBehind {
                drawRoundRect(
                    color = color,
                    cornerRadius = CornerRadius(4.dp.toPx()),
                    topLeft = Offset(0f, maximumHeight.toPx() - fillHeight.value),
                    size = Size(width.toPx(), fillHeight.value),
                )
            },
    )
}

@Preview
@Composable
fun StickGraphPreview() {
    SusuTheme {
        var ratio by remember { mutableStateOf(1f) }
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            StickGraph(ratio, Orange30)
            StickGraph(0.3f, Orange60)
            Button(
                onClick = { ratio = Random.nextFloat() },
            ) {
                Text(text = "그래프 값 변경")
            }
        }
    }
}

@Preview
@Composable
fun RecentSpentGraphPreview() {
    SusuTheme {
        Column {
            RecentSpentGraph(
                modifier = Modifier.padding(16.dp),
                spentData = listOf(
                    Pair("1월", 10000),
                    Pair("2월", 20000),
                    Pair("3월", 30000),
                    Pair("4월", 40000),
                    Pair("5월", 50000),
                    Pair("6월", 60000),
                    Pair("7월", 70000),
                    Pair("8월", 80000),
                ),
            )
            RecentSpentGraph(
                modifier = Modifier.padding(16.dp),
                spentData = listOf(
                    Pair("1월", 10000),
                    Pair("2월", 20000),
                    Pair("3월", 30000),
                    Pair("4월", 40000),
                    Pair("5월", 50000),
                    Pair("6월", 60000),
                    Pair("7월", 70000),
                    Pair("8월", 80000),
                ),
                isActive = false,
            )
        }
    }
}
