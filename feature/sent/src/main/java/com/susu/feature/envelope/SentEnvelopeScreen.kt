package com.susu.feature.envelope

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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.susu.core.designsystem.component.appbar.SusuDefaultAppBar
import com.susu.core.designsystem.component.appbar.icon.BackIcon
import com.susu.core.designsystem.component.appbar.icon.NotificationIcon
import com.susu.core.designsystem.component.appbar.icon.SearchIcon
import com.susu.core.designsystem.component.badge.BadgeColor
import com.susu.core.designsystem.component.badge.BadgeStyle
import com.susu.core.designsystem.component.badge.SusuBadge
import com.susu.core.designsystem.theme.Gray100
import com.susu.core.designsystem.theme.Gray20
import com.susu.core.designsystem.theme.Gray60
import com.susu.core.designsystem.theme.Gray90
import com.susu.core.designsystem.theme.Orange20
import com.susu.core.designsystem.theme.SusuTheme
import com.susu.core.ui.extension.collectWithLifecycle
import com.susu.core.ui.extension.toMoneyFormat
import com.susu.feature.envelope.component.EnvelopeHistoryItem
import com.susu.feature.sent.R

@Composable
fun SentEnvelopeRoute(
    viewModel: SentEnvelopeViewModel = hiltViewModel(),
    popBackStack: () -> Unit,
    navigateSentEnvelopeDetail: () -> Unit,
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    viewModel.sideEffect.collectWithLifecycle { sideEffect ->
        when (sideEffect) {
            SentEnvelopeSideEffect.PopBackStack -> popBackStack()
            else -> {}
        }
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.initData()
    }

    SentEnvelopeScreen(
        uiState = uiState,
        onClickBackIcon = viewModel::popBackStack,
        onClickEnvelopeDetail = navigateSentEnvelopeDetail,
    )
}

@Composable
fun SentEnvelopeScreen(
    uiState: SentEnvelopeState = SentEnvelopeState(),
    modifier: Modifier = Modifier,
    onClickBackIcon: () -> Unit = {},
    onClickSearchIcon: () -> Unit = {},
    onClickNotificationIcon: () -> Unit = {},
    onClickEnvelopeDetail: () -> Unit = {},
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(SusuTheme.colorScheme.background10),
    ) {
        Column {
            SusuDefaultAppBar(
                leftIcon = {
                    BackIcon(onClickBackIcon)
                },
                title = uiState.envelopeInfo[0].friend.name,
                actions = {
                    SearchIcon(onClickSearchIcon)
                    NotificationIcon(onClickNotificationIcon)
                },
            )

            Column(
                modifier = modifier
                    .padding(
                        horizontal = SusuTheme.spacing.spacing_m,
                        vertical = SusuTheme.spacing.spacing_xl,
                    ),
            ) {
                Text(
                    text = stringResource(R.string.sent_envelope_card_monee_total) + uiState.envelopeInfo[0].totalAmounts.toMoneyFormat() +
                        stringResource(R.string.sent_envelope_card_money_won),
                    style = SusuTheme.typography.title_m,
                    color = Gray100,
                )
                Spacer(modifier = modifier.size(SusuTheme.spacing.spacing_xxs))
                SusuBadge(
                    color = BadgeColor.Gray30,
                    text = "${uiState.envelopeInfo[0].sentAmounts.toFloat() + uiState.envelopeInfo[0].receivedAmounts}" +
                        stringResource(R.string.sent_envelope_card_money_won),
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
                    progress = { uiState.envelopeInfo[0].sentAmounts.toFloat() / uiState.envelopeInfo[0].totalAmounts },
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
                        text = uiState.envelopeInfo[0].sentAmounts.toMoneyFormat() + stringResource(R.string.sent_envelope_card_money_won),
                        style = SusuTheme.typography.title_xxxxs,
                        color = Gray90,
                    )
                    Text(
                        text = uiState.envelopeInfo[0].receivedAmounts.toMoneyFormat() + stringResource(R.string.sent_envelope_card_money_won),
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
                contentPadding = PaddingValues(vertical = SusuTheme.spacing.spacing_m),
                verticalArrangement = Arrangement.spacedBy(SusuTheme.spacing.spacing_m),
            ) {
                repeat(10) {
                    item {
                        EnvelopeHistoryItem(
                            onClick = onClickEnvelopeDetail,
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun SentEnvelopeScreenPreview() {
    SusuTheme {
        SentEnvelopeScreen()
    }
}
