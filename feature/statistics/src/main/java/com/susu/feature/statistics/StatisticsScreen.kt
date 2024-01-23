package com.susu.feature.statistics

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.susu.core.designsystem.component.appbar.SusuDefaultAppBar
import com.susu.core.designsystem.component.appbar.icon.LogoIcon
import com.susu.core.designsystem.theme.Gray10
import com.susu.core.designsystem.theme.Gray100
import com.susu.core.designsystem.theme.Gray40
import com.susu.core.designsystem.theme.SusuTheme
import com.susu.feature.statistics.component.RecentSpentGraph
import com.susu.feature.statistics.component.StatisticsHorizontalItem
import com.susu.feature.statistics.component.StatisticsTab
import com.susu.feature.statistics.component.StatisticsVerticalItem

@Composable
fun StatisticsRoute() {
    StatisticsScreen()
}

@Composable
fun StatisticsScreen(
    spentData: List<Pair<String, Int>> = emptyList(), // TODO: Preview 데이터
) {
    Column(
        modifier = Modifier.fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = SusuTheme.spacing.spacing_m),
        verticalArrangement = Arrangement.spacedBy(SusuTheme.spacing.spacing_xxs),
    ) {
        SusuDefaultAppBar(
            modifier = Modifier.padding(horizontal = SusuTheme.spacing.spacing_xs),
            leftIcon = { LogoIcon() },
            title = stringResource(R.string.statistics_word),
        )
        StatisticsTab(
            modifier = Modifier
                .height(52.dp)
                .padding(vertical = SusuTheme.spacing.spacing_xxs),
            selectedTab = StatisticsTab.MY,
            onTabSelect = {},
        )
        RecentSpentGraph(
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
            Text(
                text = stringResource(R.string.word_month_format, stringResource(id = R.string.word_unknown)),
                style = SusuTheme.typography.title_xs,
                color = Gray40,
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {
            StatisticsVerticalItem(
                modifier = Modifier.weight(1f),
                title = stringResource(R.string.statistics_most_susu_relationship),
                content = "",
                description = "",
            )
            Spacer(modifier = Modifier.width(SusuTheme.spacing.spacing_xxs))
            StatisticsVerticalItem(
                modifier = Modifier.weight(1f),
                title = stringResource(R.string.statistics_most_susu_event),
                content = "",
                description = "",
            )
        }
        StatisticsHorizontalItem(
            title = stringResource(R.string.statistics_most_received_money),
            name = "김수수",
            money = 0,
        )
        StatisticsHorizontalItem(
            title = stringResource(R.string.statistics_most_sent_money),
            name = "양수수",
            money = 0,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SentScreenPreview() {
    SusuTheme {
        StatisticsScreen(
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
