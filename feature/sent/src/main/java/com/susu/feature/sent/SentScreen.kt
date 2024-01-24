package com.susu.feature.sent

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
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.susu.core.designsystem.component.appbar.SusuDefaultAppBar
import com.susu.core.designsystem.component.appbar.icon.LogoIcon
import com.susu.core.designsystem.component.appbar.icon.NotificationIcon
import com.susu.core.designsystem.component.appbar.icon.SearchIcon
import com.susu.core.designsystem.component.button.GhostButtonColor
import com.susu.core.designsystem.component.button.SmallButtonStyle
import com.susu.core.designsystem.component.button.SusuFloatingButton
import com.susu.core.designsystem.component.button.SusuGhostButton
import com.susu.core.designsystem.theme.Gray100
import com.susu.core.designsystem.theme.Gray50
import com.susu.core.designsystem.theme.SusuTheme
import com.susu.core.ui.extension.OnBottomReached
import com.susu.core.ui.extension.collectWithLifecycle
import com.susu.feature.sent.component.SentCard

@Composable
fun SentRoute(
    viewModel: SentViewModel = hiltViewModel(),
    padding: PaddingValues,
    navigateSentEnvelope: () -> Unit,
    navigateSentEnvelopeAdd: () -> Unit,
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    val envelopesListState = rememberLazyListState()

    viewModel.sideEffect.collectWithLifecycle { sideEffect ->
        when (sideEffect) {
            SentEffect.NavigateEnvelopeAdd -> navigateSentEnvelopeAdd()
            is SentEffect.NavigateEnvelope -> navigateSentEnvelope()
        }
    }

    envelopesListState.OnBottomReached {
        viewModel.getEnvelopesList()
    }

    SentScreen(
        uiState = uiState,
        envelopesListState = envelopesListState,
        padding = padding,
        onClickHistoryShowAll = viewModel::navigateSentEnvelope,
        onClickAddEnvelope = viewModel::navigateSentAdd,
    )
}

@Composable
fun SentScreen(
    uiState: SentState = SentState(),
    envelopesListState: LazyListState = rememberLazyListState(),
    padding: PaddingValues,
    modifier: Modifier = Modifier,
    onClickSearchIcon: () -> Unit = {},
    onClickNotificationIcon: () -> Unit = {},
    onClickHistoryShowAll: () -> Unit = {},
    onClickAddEnvelope: () -> Unit = {},
) {
    Box(
        modifier = Modifier
            .background(SusuTheme.colorScheme.background15)
            .padding(padding)
            .fillMaxSize(),
    ) {
        Column {
            SusuDefaultAppBar(
                leftIcon = {
                    Spacer(modifier = modifier.size(SusuTheme.spacing.spacing_xs))
                    LogoIcon()
                },
                title = stringResource(R.string.sent_screen_appbar_title),
                actions = {
                    SearchIcon(onClickSearchIcon)
                    NotificationIcon(onClickNotificationIcon)
                },
            )

            LazyColumn(
                modifier = modifier.fillMaxSize(),
                state = envelopesListState,
                verticalArrangement = Arrangement.spacedBy(SusuTheme.spacing.spacing_xxs),
                contentPadding = PaddingValues(SusuTheme.spacing.spacing_m),
            ) {
                item {
                    FilterSection(
                        padding = PaddingValues(
                            bottom = SusuTheme.spacing.spacing_xxs,
                        ),
                    )
                }

                items(
                    items = uiState.envelopesList,
                    key = { it.friend.id },
                ) {
                    // 전체 보기 버튼
                    SentCard(
                        friend = it.friend,
                        totalAmounts = it.totalAmounts,
                        sentAmounts = it.sentAmounts,
                        receivedAmounts = it.receivedAmounts,
                        onClick = onClickHistoryShowAll,
                    )
                }
            }

            if (uiState.showEmptyEnvelopes) {
                FilterSection(
                    padding = PaddingValues(SusuTheme.spacing.spacing_m),
                )
                EmptyView(
                    onClickAddEnvelope = onClickAddEnvelope
                )
            }
        }

        SusuFloatingButton(
            modifier = modifier
                .align(Alignment.BottomEnd)
                .padding(SusuTheme.spacing.spacing_l),
            onClick = onClickAddEnvelope,
        )
    }
}

@Composable
fun FilterSection(
    modifier: Modifier = Modifier,
    padding: PaddingValues,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(padding),
        horizontalArrangement = Arrangement.spacedBy(SusuTheme.spacing.spacing_xxs),
    ) {
        SusuGhostButton(
            color = GhostButtonColor.Black,
            style = SmallButtonStyle.height32,
            text = stringResource(com.susu.core.ui.R.string.word_align_recently),
            leftIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_sort),
                    contentDescription = stringResource(com.susu.core.ui.R.string.word_align_recently),
                    tint = Gray100,
                    modifier = modifier.size(16.dp),
                )
            },
        )
        SusuGhostButton(
            color = GhostButtonColor.Black,
            style = SmallButtonStyle.height32,
            text = stringResource(com.susu.core.ui.R.string.word_filter),
            leftIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_filter),
                    contentDescription = stringResource(com.susu.core.ui.R.string.word_filter),
                    tint = Gray100,
                    modifier = modifier.size(16.dp),
                )
            },
        )
    }
}

@Composable
fun EmptyView(
    modifier: Modifier = Modifier,
    onClickAddEnvelope: () -> Unit = {},
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = stringResource(R.string.sent_screen_empty_view_title),
            color = Gray50,
            style = SusuTheme.typography.text_s,
        )
        Spacer(modifier = modifier.size(SusuTheme.spacing.spacing_m))
        SusuGhostButton(
            color = GhostButtonColor.Black,
            style = SmallButtonStyle.height40,
            text = stringResource(R.string.sent_screen_empty_view_add_button),
            onClick = onClickAddEnvelope,
        )
    }
}

@Preview
@Composable
fun SentScreenPreview() {
    SusuTheme {
        SentScreen(padding = PaddingValues(0.dp))
    }
}
