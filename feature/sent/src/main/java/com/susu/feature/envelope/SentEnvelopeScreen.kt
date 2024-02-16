package com.susu.feature.envelope

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.susu.core.designsystem.component.appbar.SusuDefaultAppBar
import com.susu.core.designsystem.component.appbar.icon.BackIcon
import com.susu.core.designsystem.component.badge.BadgeColor
import com.susu.core.designsystem.component.badge.BadgeStyle
import com.susu.core.designsystem.component.badge.SusuBadge
import com.susu.core.designsystem.component.button.SusuFloatingButton
import com.susu.core.designsystem.theme.Gray10
import com.susu.core.designsystem.theme.Gray100
import com.susu.core.designsystem.theme.Gray20
import com.susu.core.designsystem.theme.Gray60
import com.susu.core.designsystem.theme.Gray90
import com.susu.core.designsystem.theme.Orange20
import com.susu.core.designsystem.theme.SusuTheme
import com.susu.core.model.Friend
import com.susu.core.ui.extension.OnBottomReached
import com.susu.core.ui.extension.collectWithLifecycle
import com.susu.core.ui.extension.toMoneyFormat
import com.susu.feature.envelope.component.EnvelopeHistoryItem
import com.susu.feature.sent.R
import kotlinx.datetime.toJavaLocalDateTime
import kotlin.math.abs

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SentEnvelopeRoute(
    viewModel: SentEnvelopeViewModel = hiltViewModel(),
    popBackStack: () -> Unit,
    popBackStackWithDeleteFriendId: (Long) -> Unit,
    popBackStackWithEditedFriendId: (Long) -> Unit,
    editedFriendId: Long?,
    navigateSentEnvelopeDetail: (Long) -> Unit,
    navigateSentEnvelopeAdd: (Friend) -> Unit,
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    val historyListState = rememberLazyListState()

    val refreshState = rememberPullToRefreshState(
        positionalThreshold = 100.dp,
    )

    viewModel.sideEffect.collectWithLifecycle { sideEffect ->
        when (sideEffect) {
            SentEnvelopeSideEffect.PopBackStack -> {
                editedFriendId?.let { popBackStackWithEditedFriendId(it) } ?: popBackStack()
            }

            is SentEnvelopeSideEffect.NavigateEnvelopeDetail -> navigateSentEnvelopeDetail(sideEffect.id)
            is SentEnvelopeSideEffect.PopBackStackWithDeleteFriendId -> popBackStackWithDeleteFriendId(sideEffect.id)
            is SentEnvelopeSideEffect.NavigateEnvelopeAdd -> navigateSentEnvelopeAdd(sideEffect.friend)
        }
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.initData()
    }

    historyListState.OnBottomReached {
        viewModel.getEnvelopeHistoryList()
    }

    LaunchedEffect(key1 = refreshState.isRefreshing) {
        if (refreshState.isRefreshing.not()) return@LaunchedEffect

        viewModel.initData {
            refreshState.endRefresh()
        }
    }

    BackHandler {
        editedFriendId?.let { popBackStackWithEditedFriendId(it) } ?: popBackStack()
    }

    SentEnvelopeScreen(
        uiState = uiState,
        refreshState = refreshState,
        onClickBackIcon = viewModel::popBackStack,
        onClickEnvelopeDetail = viewModel::navigateSentEnvelopeDetail,
        onClickAddEnvelope = viewModel::navigateSentEnvelopeAdd,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SentEnvelopeScreen(
    modifier: Modifier = Modifier,
    refreshState: PullToRefreshState = rememberPullToRefreshState(),
    uiState: SentEnvelopeState = SentEnvelopeState(),
    historyListState: LazyListState = rememberLazyListState(),
    onClickBackIcon: () -> Unit = {},
    onClickEnvelopeDetail: (Long) -> Unit = {},
    onClickAddEnvelope: () -> Unit = {},
) {
    val sent = uiState.envelopeInfo.sentAmounts
    val received = uiState.envelopeInfo.receivedAmounts
    val total = uiState.envelopeInfo.totalAmounts
    val moneyDiff = abs(sent - received).toMoneyFormat()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(SusuTheme.colorScheme.background10)
            .nestedScroll(refreshState.nestedScrollConnection),
    ) {
        Column {
            SusuDefaultAppBar(
                leftIcon = {
                    BackIcon(onClickBackIcon)
                },
                title = uiState.envelopeInfo.friend.name,
            )

            Column(
                modifier = modifier
                    .padding(
                        horizontal = SusuTheme.spacing.spacing_m,
                        vertical = SusuTheme.spacing.spacing_xl,
                    ),
            ) {
                Text(
                    text = stringResource(R.string.sent_envelope_card_money_total, uiState.envelopeInfo.totalAmounts.toMoneyFormat()),
                    style = SusuTheme.typography.title_m,
                    color = Gray100,
                )
                Spacer(modifier = modifier.size(SusuTheme.spacing.spacing_xxs))
                SusuBadge(
                    color = BadgeColor.Gray30,
                    text = if (sent > received) {
                        stringResource(R.string.sent_envelope_money_diff_minus, moneyDiff)
                    } else if (sent < received) {
                        stringResource(R.string.sent_envelope_money_diff_plus, moneyDiff)
                    } else {
                        stringResource(R.string.sent_envelope_money_diff_same, moneyDiff)
                    },
                    padding = BadgeStyle.smallBadge,
                )
                Spacer(modifier = modifier.size(SusuTheme.spacing.spacing_xl))
                Row(
                    modifier = modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        text = stringResource(id = R.string.sent_screen_envelope_sent),
                        style = SusuTheme.typography.title_xxxs,
                        color = Gray90,
                    )
                    Text(
                        text = stringResource(id = R.string.sent_screen_envelope_received),
                        style = SusuTheme.typography.title_xxxs,
                        color = Gray60,
                    )
                }
                LinearProgressIndicator(
                    progress = { sent.toFloat() / total },
                    color = SusuTheme.colorScheme.primary,
                    trackColor = Orange20,
                    strokeCap = StrokeCap.Round,
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(vertical = SusuTheme.spacing.spacing_xxxxs),
                )
                Row(
                    modifier = modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        text = stringResource(R.string.sent_envelope_card_money_sent_received, sent.toMoneyFormat()),
                        style = SusuTheme.typography.title_xxxxs,
                        color = Gray90,
                    )
                    Text(
                        text = stringResource(R.string.sent_envelope_card_money_sent_received, received.toMoneyFormat()),
                        style = SusuTheme.typography.title_xxxxs,
                        color = Gray60,
                    )
                }
            }
            HorizontalDivider(
                thickness = 8.dp,
                color = Gray20,
            )

            LazyColumn(
                state = historyListState,
                contentPadding = PaddingValues(vertical = SusuTheme.spacing.spacing_m),
                verticalArrangement = Arrangement.spacedBy(SusuTheme.spacing.spacing_m),
            ) {
                items(
                    items = uiState.envelopeHistoryList,
                    key = { it.envelope.id },
                ) {
                    EnvelopeHistoryItem(
                        type = it.envelope.type,
                        event = it.category!!.category,
                        date = it.envelope.handedOverAt.toJavaLocalDateTime(),
                        money = it.envelope.amount,
                        onClick = { onClickEnvelopeDetail(it.envelope.id) },
                    )
                }
            }
        }

        PullToRefreshContainer(
            modifier = Modifier.align(Alignment.TopCenter),
            state = refreshState,
            containerColor = Gray10,
        )

        SusuFloatingButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(SusuTheme.spacing.spacing_l),
            onClick = onClickAddEnvelope,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun SentEnvelopeScreenPreview() {
    SusuTheme {
        SentEnvelopeScreen()
    }
}
