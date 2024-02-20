package com.susu.feature.statistics.content.my

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
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
import com.susu.core.ui.DialogToken
import com.susu.core.ui.extension.collectWithLifecycle
import com.susu.core.ui.extension.susuClickable
import com.susu.feature.statistics.R
import com.susu.feature.statistics.component.RecentSpentGraph
import com.susu.feature.statistics.component.StatisticsHorizontalItem
import com.susu.feature.statistics.component.StatisticsVerticalItem
import kotlinx.collections.immutable.toPersistentList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyStatisticsRoute(
    modifier: Modifier,
    viewModel: MyStatisticsViewModel = hiltViewModel(),
    handleException: (Throwable, () -> Unit) -> Unit,
    onShowDialog: (DialogToken) -> Unit,
    navigateToSent: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    val refreshState = rememberPullToRefreshState(
        positionalThreshold = 100.dp,
    )

    viewModel.sideEffect.collectWithLifecycle { sideEffect ->
        when (sideEffect) {
            is MyStatisticsEffect.HandleException -> handleException(sideEffect.throwable, sideEffect.retry)
            MyStatisticsEffect.ShowDataNotExistDialog -> onShowDialog(
                DialogToken(
                    title = context.getString(R.string.statistics_my_dialog_title),
                    text = context.getString(R.string.statistics_my_dialog_description),
                    confirmText = context.getString(R.string.statistics_my_dialog_confirm),
                    dismissText = context.getString(com.susu.core.ui.R.string.word_close),
                    onConfirmRequest = navigateToSent,
                ),
            )
        }
    }

    LaunchedEffect(key1 = refreshState.isRefreshing) {
        if (refreshState.isRefreshing.not()) return@LaunchedEffect

        viewModel.getMyStatistics {
            refreshState.endRefresh()
        }
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.getMyStatistics()
    }

    MyStatisticsContent(
        uiState = uiState,
        refreshState = refreshState,
        modifier = modifier,
        onShowDataNotExistDialog = viewModel::showDataNotExistDialog,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyStatisticsContent(
    modifier: Modifier = Modifier,
    uiState: MyStatisticsState,
    refreshState: PullToRefreshState = rememberPullToRefreshState(),
    onShowDataNotExistDialog: () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .nestedScroll(refreshState.nestedScrollConnection),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(SusuTheme.spacing.spacing_xxs),
        ) {
            RecentSpentGraph(
                modifier = Modifier.susuClickable(
                    rippleEnabled = false,
                    onClick = onShowDataNotExistDialog,
                    runIf = uiState.isBlind,
                ),
                isActive = !uiState.isBlind,
                spentData = uiState.statistics.recentSpent.toPersistentList(),
                maximumAmount = uiState.statistics.recentMaximumSpent,
                totalAmount = uiState.statistics.recentTotalSpent,
                graphTitle = stringResource(R.string.statistics_recent_8_total_money),
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .susuClickable(
                        rippleEnabled = false,
                        onClick = onShowDataNotExistDialog,
                        runIf = uiState.isBlind,
                    )
                    .background(color = Gray10, shape = RoundedCornerShape(4.dp))
                    .padding(SusuTheme.spacing.spacing_m),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(text = stringResource(R.string.statistics_most_spent_month), style = SusuTheme.typography.title_xs, color = Gray100)
                if (uiState.isBlind) {
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
                    modifier = Modifier.weight(1f)
                        .susuClickable(
                            rippleEnabled = false,
                            onClick = onShowDataNotExistDialog,
                            runIf = uiState.isBlind,
                        ),
                    title = stringResource(R.string.statistics_most_susu_relationship),
                    content = uiState.statistics.mostRelationship.title,
                    count = uiState.statistics.mostRelationship.value,
                    isActive = !uiState.isBlind,
                )
                Spacer(modifier = Modifier.width(SusuTheme.spacing.spacing_xxs))
                StatisticsVerticalItem(
                    modifier = Modifier.weight(1f)
                        .susuClickable(
                            rippleEnabled = false,
                            onClick = onShowDataNotExistDialog,
                            runIf = uiState.isBlind,
                        ),
                    title = stringResource(R.string.statistics_most_susu_event),
                    content = uiState.statistics.mostCategory.title,
                    count = uiState.statistics.mostCategory.value,
                    isActive = !uiState.isBlind,
                )
            }
            StatisticsHorizontalItem(
                modifier = Modifier.susuClickable(
                    rippleEnabled = false,
                    onClick = onShowDataNotExistDialog,
                    runIf = uiState.isBlind,
                ),
                title = stringResource(R.string.statistics_most_received_money),
                name = uiState.statistics.highestAmountReceived.title,
                money = uiState.statistics.highestAmountReceived.value,
                isActive = !uiState.isBlind,
            )
            StatisticsHorizontalItem(
                modifier = Modifier.susuClickable(
                    rippleEnabled = false,
                    onClick = onShowDataNotExistDialog,
                    runIf = uiState.isBlind,
                ),
                title = stringResource(R.string.statistics_most_sent_money),
                name = uiState.statistics.highestAmountSent.title,
                money = uiState.statistics.highestAmountSent.value,
                isActive = !uiState.isBlind,
            )
            Spacer(modifier = Modifier.height(SusuTheme.spacing.spacing_xxxl))
        }

        PullToRefreshContainer(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = (-120).dp),
            state = refreshState,
            containerColor = Gray10,
        )

        if (uiState.isLoading) {
            LoadingScreen(
                modifier = Modifier.align(Alignment.Center),
            )
        }
    }
}
