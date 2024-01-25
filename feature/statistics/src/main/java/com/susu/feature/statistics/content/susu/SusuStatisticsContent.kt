package com.susu.feature.statistics.content.susu

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.susu.core.designsystem.theme.Blue60
import com.susu.core.designsystem.theme.Gray10
import com.susu.core.designsystem.theme.Gray100
import com.susu.core.designsystem.theme.Gray40
import com.susu.core.designsystem.theme.SusuTheme
import com.susu.feature.statistics.R
import com.susu.feature.statistics.component.RecentSpentGraph
import com.susu.feature.statistics.component.StatisticsHorizontalItem
import com.susu.feature.statistics.component.StatisticsVerticalItem

@Composable
fun SusuStatisticsRoute() {
    SusuStatisticsScreen()
}

@Composable
fun SusuStatisticsScreen(
    modifier: Modifier = Modifier,
    isBlind: Boolean = true,
) {
    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(SusuTheme.spacing.spacing_xxs),
        ) {
            SusuStatisticsOptionSlot()
            StatisticsHorizontalItem(
                title = "관계 별 퍙균 수수",
                name = "",
                money = 0,
                isActive = !isBlind,
            )
            StatisticsHorizontalItem(
                title = "경조사 카테고리 별 평균 수수",
                name = "",
                money = 0,
                isActive = !isBlind,
            )

            RecentSpentGraph(
                isActive = !isBlind,
                graphTitle = "올해 쓴 금액",
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Gray10, shape = RoundedCornerShape(4.dp))
                    .padding(SusuTheme.spacing.spacing_m),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(text = stringResource(R.string.statistics_most_spent_month), style = SusuTheme.typography.title_xs, color = Gray100)
                if (isBlind) {
                    Text(
                        text = stringResource(R.string.word_month_format, stringResource(id = R.string.word_unknown)),
                        style = SusuTheme.typography.title_xs,
                        color = Gray40,
                    )
                } else {
                    Text(
                        text = stringResource(R.string.word_month_format, ""),
                        style = SusuTheme.typography.title_xs,
                        color = Blue60,
                    )
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                StatisticsVerticalItem(
                    modifier = Modifier.weight(1f),
                    title = stringResource(R.string.statistics_most_susu_relationship),
                    content = "",
                    count = 0,
                    isActive = !isBlind,
                )
                Spacer(modifier = Modifier.width(SusuTheme.spacing.spacing_xxs))
                StatisticsVerticalItem(
                    modifier = Modifier.weight(1f),
                    title = stringResource(R.string.statistics_most_susu_event),
                    content = "",
                    count = 0,
                    isActive = !isBlind,
                )
            }
        }

//        if (uiState.isLoading) {
//            LoadingScreen(
//                modifier = Modifier.align(Alignment.Center),
//            )
//        }
    }
}

@Preview(showBackground = true)
@Composable
fun SusuStatisticsScreenPreview() {
    SusuTheme {
        SusuStatisticsScreen(
            isBlind = false,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SusuStatisticsScreenBlindPreview() {
    SusuTheme {
        SusuStatisticsScreen(
            isBlind = true,
        )
    }
}
