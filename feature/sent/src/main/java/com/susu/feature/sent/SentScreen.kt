package com.susu.feature.sent

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.susu.core.designsystem.component.appbar.SusuDefaultAppBar
import com.susu.core.designsystem.component.appbar.icon.LogoIcon
import com.susu.core.designsystem.component.appbar.icon.SearchIcon
import com.susu.core.designsystem.component.button.FilledButtonColor
import com.susu.core.designsystem.component.button.FilterButton
import com.susu.core.designsystem.component.button.GhostButtonColor
import com.susu.core.designsystem.component.button.SelectedFilterButton
import com.susu.core.designsystem.component.button.SmallButtonStyle
import com.susu.core.designsystem.component.button.SusuFloatingButton
import com.susu.core.designsystem.component.button.SusuGhostButton
import com.susu.core.designsystem.theme.Gray50
import com.susu.core.designsystem.theme.SusuTheme
import com.susu.core.model.Friend
import com.susu.core.ui.extension.OnBottomReached
import com.susu.core.ui.extension.collectWithLifecycle
import com.susu.core.ui.extension.toMoneyFormat
import com.susu.feature.sent.component.SentCard
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState

@Composable
fun SentRoute(
    viewModel: SentViewModel = hiltViewModel(),
    deletedFriendId: Long?,
    refresh: Boolean?,
    filter: String?,
    padding: PaddingValues,
    navigateSentEnvelope: (Long) -> Unit,
    navigateSentEnvelopeAdd: () -> Unit,
    navigateSentEnvelopeSearch: () -> Unit,
    navigateEnvelopeFilter: (String) -> Unit,
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    val envelopesListState = rememberLazyListState()

    viewModel.sideEffect.collectWithLifecycle { sideEffect ->
        when (sideEffect) {
            SentEffect.NavigateEnvelopeAdd -> navigateSentEnvelopeAdd()
            is SentEffect.NavigateEnvelope -> navigateSentEnvelope(sideEffect.id)
            SentEffect.NavigateEnvelopeSearch -> navigateSentEnvelopeSearch()
            is SentEffect.NavigateEnvelopeFilter -> navigateEnvelopeFilter(sideEffect.filter)
        }
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.getEnvelopesList(refresh)
        viewModel.filterIfNeed(filter)
        if (deletedFriendId != null) {
            viewModel.deleteEmptyFriendStatistics(deletedFriendId)
        }
    }

    envelopesListState.OnBottomReached {
        if (uiState.envelopesList.isNotEmpty()) {
            viewModel.getEnvelopesList(refresh = false)
        }
    }

    SentScreen(
        uiState = uiState,
        envelopesListState = envelopesListState,
        padding = padding,
        onClickHistoryShowAll = viewModel::navigateSentEnvelope,
        onClickAddEnvelope = viewModel::navigateSentAdd,
        onClickSearchIcon = viewModel::navigateSentEnvelopeSearch,
        onClickHistory = { friendId ->
            viewModel.getEnvelopesHistoryList(friendId)
        },
        onClickAlignButton = {},
        onClickFilterButton = viewModel::navigateEnvelopeFilter,
        onClickFriendClose = viewModel::unselectFriend,
        onClickMoneyClose = viewModel::removeMoney,
    )
}

@Composable
fun SentScreen(
    modifier: Modifier = Modifier,
    uiState: SentState = SentState(),
    envelopesListState: LazyListState = rememberLazyListState(),
    padding: PaddingValues,
    onClickSearchIcon: () -> Unit = {},
    onClickHistory: (Long) -> Unit = {},
    onClickHistoryShowAll: (Long) -> Unit = {},
    onClickAddEnvelope: () -> Unit = {},
    onClickAlignButton: () -> Unit = {},
    onClickFilterButton: () -> Unit = {},
    onClickFriendClose: (Friend) -> Unit = {},
    onClickMoneyClose: () -> Unit = {},
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
                },
            )

            val state = rememberCollapsingToolbarScaffoldState()
            CollapsingToolbarScaffold(
                modifier = Modifier.fillMaxSize(),
                state = state,
                scrollStrategy = ScrollStrategy.EnterAlways,
                toolbar = {
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(32.dp),
                    )

                    FilterSection(
                        modifier = Modifier.graphicsLayer {
                            alpha = state.toolbarState.progress
                        },
                        uiState = uiState,
                        padding = PaddingValues(
                            top = SusuTheme.spacing.spacing_m,
                        ),
                        onClickAlignButton = onClickAlignButton,
                        onClickFilterButton = onClickFilterButton,
                        onClickFriendClose = onClickFriendClose,
                        onClickMoneyClose = onClickMoneyClose,
                    )
                },
            ) {
                LazyColumn(
                    modifier = modifier.fillMaxSize(),
                    state = envelopesListState,
                    verticalArrangement = Arrangement.spacedBy(SusuTheme.spacing.spacing_xxs),
                    contentPadding = PaddingValues(SusuTheme.spacing.spacing_m),
                ) {
                    items(
                        items = uiState.envelopesList,
                        key = { it.friend.id },
                    ) {
                        SentCard(
                            uiState = it,
                            friend = it.friend,
                            totalAmounts = it.totalAmounts,
                            sentAmounts = it.sentAmounts,
                            receivedAmounts = it.receivedAmounts,
                            onClickHistory = onClickHistory,
                            onClickHistoryShowAll = onClickHistoryShowAll,
                        )
                    }
                }

                if (uiState.showEmptyEnvelopes) {
                    EmptyView(
                        onClickAddEnvelope = onClickAddEnvelope,
                    )
                }
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
    modifier: Modifier,
    uiState: SentState = SentState(),
    padding: PaddingValues,
    onClickAlignButton: () -> Unit,
    onClickFilterButton: () -> Unit,
    onClickFriendClose: (Friend) -> Unit,
    onClickMoneyClose: () -> Unit,
) {
    Row(
        modifier = modifier
            .padding(
                padding,
            )
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(SusuTheme.spacing.spacing_xxs),
    ) {
        Spacer(modifier = Modifier.size(SusuTheme.spacing.spacing_xxs))

        SusuGhostButton(
            color = GhostButtonColor.Black,
            style = SmallButtonStyle.height32,
            text = stringResource(id = EnvelopeAlign.entries[uiState.selectedAlignPosition].stringResId),
            leftIcon = {
                Icon(
                    painter = painterResource(id = com.susu.core.ui.R.drawable.ic_align),
                    contentDescription = null,
                )
            },
            onClick = onClickAlignButton,
        )

        FilterButton(uiState.isFiltered, onClickFilterButton)

        uiState.selectedFriendList.forEach { friend ->
            SelectedFilterButton(
                color = FilledButtonColor.Black,
                style = SmallButtonStyle.height32,
                name = friend.name,
                onClickCloseIcon = { onClickFriendClose(friend) },
            )
        }

        if (uiState.fromAmount != null || uiState.toAmount != null) {
            SelectedFilterButton(
                color = FilledButtonColor.Black,
                style = SmallButtonStyle.height32,
                name = "${uiState.fromAmount?.toMoneyFormat() ?: ""}~${uiState.toAmount?.toMoneyFormat() ?: ""}",
                onClickCloseIcon = onClickMoneyClose,
            )
        }

        Spacer(modifier = Modifier.size(SusuTheme.spacing.spacing_xxs))
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
