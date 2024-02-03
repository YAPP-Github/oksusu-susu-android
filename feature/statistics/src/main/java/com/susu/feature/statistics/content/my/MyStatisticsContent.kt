package com.susu.feature.statistics.content.my

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.susu.core.designsystem.component.screen.LoadingScreen
import com.susu.core.designsystem.theme.Blue60
import com.susu.core.designsystem.theme.Gray10
import com.susu.core.designsystem.theme.Gray100
import com.susu.core.designsystem.theme.Gray40
import com.susu.core.designsystem.theme.SusuTheme
import com.susu.core.ui.extension.collectWithLifecycle
import com.susu.feature.statistics.R
import com.susu.feature.statistics.component.RecentSpentGraph
import com.susu.feature.statistics.component.StatisticsHorizontalItem
import com.susu.feature.statistics.component.StatisticsVerticalItem
import kotlinx.collections.immutable.toPersistentList

@Composable
fun MyStatisticsRoute(
    isBlind: Boolean,
    modifier: Modifier,
    viewModel: MyStatisticsViewModel = hiltViewModel(),
    handleException: (Throwable, () -> Unit) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    viewModel.sideEffect.collectWithLifecycle { sideEffect ->
        when (sideEffect) {
            is MyStatisticsEffect.HandleException -> handleException(sideEffect.throwable, sideEffect.retry)
        }
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.getMyStatistics()
    }

    MyStatisticsContent(
        uiState = uiState,
        isBlind = isBlind,
        modifier = modifier,
    )
}

@Composable
fun MyStatisticsContent(
    uiState: MyStatisticsState,
    isBlind: Boolean,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(SusuTheme.spacing.spacing_xxs),
        ) {
            RecentSpentGraph(
                isActive = !isBlind,
                spentData = uiState.statistics.recentSpent.toPersistentList(),
                maximumAmount = uiState.statistics.recentMaximumSpent,
                totalAmount = uiState.statistics.recentTotalSpent,
                graphTitle = stringResource(R.string.statistics_recent_8_total_money),
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
                        text = stringResource(R.string.word_month_format, uiState.statistics.mostSpentMonth.toString()),
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
                    content = uiState.statistics.mostRelationship.title,
                    count = uiState.statistics.mostRelationship.value,
                    isActive = !isBlind,
                )
                Spacer(modifier = Modifier.width(SusuTheme.spacing.spacing_xxs))
                StatisticsVerticalItem(
                    modifier = Modifier.weight(1f),
                    title = stringResource(R.string.statistics_most_susu_event),
                    content = uiState.statistics.mostCategory.title,
                    count = uiState.statistics.mostCategory.value,
                    isActive = !isBlind,
                )
            }
            StatisticsHorizontalItem(
                title = stringResource(R.string.statistics_most_received_money),
                name = uiState.statistics.highestAmountReceived.title,
                money = uiState.statistics.highestAmountReceived.value,
                isActive = !isBlind,
            )
            StatisticsHorizontalItem(
                title = stringResource(R.string.statistics_most_sent_money),
                name = uiState.statistics.highestAmountSent.title,
                money = uiState.statistics.highestAmountSent.value,
                isActive = !isBlind,
            )
        }

        if (uiState.isLoading) {
            LoadingScreen(
                modifier = Modifier.align(Alignment.Center),
            )
        }
    }
}
