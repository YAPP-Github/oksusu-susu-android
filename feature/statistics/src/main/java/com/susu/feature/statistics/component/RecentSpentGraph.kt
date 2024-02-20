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
import com.susu.core.designsystem.component.text.AnimatedCounterText
import com.susu.core.designsystem.theme.Blue60
import com.susu.core.designsystem.theme.Gray10
import com.susu.core.designsystem.theme.Gray100
import com.susu.core.designsystem.theme.Gray40
import com.susu.core.designsystem.theme.Gray90
import com.susu.core.designsystem.theme.Orange30
import com.susu.core.designsystem.theme.Orange60
import com.susu.core.designsystem.theme.SusuTheme
import com.susu.core.model.StatisticsElement
import com.susu.feature.statistics.R
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlin.random.Random

@Composable
fun RecentSpentGraph(
    modifier: Modifier = Modifier,
    isActive: Boolean = true,
    spentData: PersistentList<StatisticsElement> = persistentListOf(),
    totalAmount: Long = 0L,
    maximumAmount: Long = 0L,
    graphTitle: String = "",
) {
    if (isActive) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .background(color = Gray10, shape = RoundedCornerShape(4.dp))
                .padding(SusuTheme.spacing.spacing_xxs),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(SusuTheme.spacing.spacing_xxs),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = graphTitle,
                    style = SusuTheme.typography.title_xs,
                    color = Gray100,
                )
                AnimatedCounterText(
                    number = totalAmount,
                    style = SusuTheme.typography.title_xs,
                    color = Blue60,
                    postfix = stringResource(id = R.string.statistics_total_man_postfix),
                )
            }
            Spacer(modifier = Modifier.height(SusuTheme.spacing.spacing_xxs))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(SusuTheme.spacing.spacing_xxs),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                if (maximumAmount > 0) {
                    spentData.forEachIndexed { i, data ->
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            StickGraph(
                                ratio = data.value.toFloat() / maximumAmount,
                                color = if (i == spentData.lastIndex) {
                                    Orange60
                                } else {
                                    Orange30
                                },
                            )
                            Spacer(modifier = Modifier.height(SusuTheme.spacing.spacing_xxxxs))
                            Text(
                                text = stringResource(id = R.string.word_month_format, data.title),
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
    } else {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .background(color = Gray10, shape = RoundedCornerShape(4.dp))
                .padding(SusuTheme.spacing.spacing_m),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = graphTitle,
                style = SusuTheme.typography.title_xs,
                color = Gray100,
            )
            Text(
                text = stringResource(R.string.statistics_man_format, stringResource(R.string.word_unknown)),
                style = SusuTheme.typography.title_xs,
                color = Gray40,
            )
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
                    StatisticsElement("1월", 10000),
                    StatisticsElement("2월", 20000),
                    StatisticsElement("3월", 30000),
                    StatisticsElement("4월", 40000),
                    StatisticsElement("5월", 50000),
                    StatisticsElement("6월", 60000),
                    StatisticsElement("7월", 70000),
                    StatisticsElement("8월", 80000),
                ).toPersistentList(),
            )
            RecentSpentGraph(
                modifier = Modifier.padding(16.dp),
                spentData = listOf(
                    StatisticsElement("1월", 10000),
                    StatisticsElement("2월", 20000),
                    StatisticsElement("3월", 30000),
                    StatisticsElement("4월", 40000),
                    StatisticsElement("5월", 50000),
                    StatisticsElement("6월", 60000),
                    StatisticsElement("7월", 70000),
                    StatisticsElement("8월", 80000),
                ).toPersistentList(),
                isActive = false,
            )
        }
    }
}
