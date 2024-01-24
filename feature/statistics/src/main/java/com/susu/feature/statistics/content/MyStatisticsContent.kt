package com.susu.feature.statistics.content

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
fun MyStatisticsRoute(
    isBlind: Boolean,
    modifier: Modifier,
) {
    MyStatisticsContent(
        isBlind = isBlind,
        modifier = modifier,
    )
}

@Composable
fun MyStatisticsContent(
    isBlind: Boolean,
    modifier: Modifier = Modifier,
    spentData: List<Pair<String, Int>> = emptyList(), // TODO: Preview 데이터
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(SusuTheme.spacing.spacing_xxs),
    ) {
        RecentSpentGraph(
            isActive = !isBlind,
            spentData = spentData, // TODO: 서버 값으로 교체
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
                    text = stringResource(R.string.word_month_format, "3"),
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
                description = "",
                isActive = !isBlind,
            )
            Spacer(modifier = Modifier.width(SusuTheme.spacing.spacing_xxs))
            StatisticsVerticalItem(
                modifier = Modifier.weight(1f),
                title = stringResource(R.string.statistics_most_susu_event),
                content = "",
                description = "",
                isActive = !isBlind,
            )
        }
        StatisticsHorizontalItem(
            title = stringResource(R.string.statistics_most_received_money),
            name = "김수수",
            money = 0,
            isActive = !isBlind,
        )
        StatisticsHorizontalItem(
            title = stringResource(R.string.statistics_most_sent_money),
            name = "양수수",
            money = 0,
            isActive = !isBlind,
        )
    }
}

@Preview
@Composable
fun MyStatisticsContentPreview() {
    SusuTheme {
        MyStatisticsContent(
            isBlind = false,
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
    }
}

@Preview
@Composable
fun MyStatisticsContentBlindPreview() {
    SusuTheme {
        MyStatisticsContent(
            isBlind = true,
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
    }
}
