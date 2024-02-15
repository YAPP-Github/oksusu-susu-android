package com.susu.feature.sent

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.susu.core.designsystem.component.appbar.SusuDefaultAppBar
import com.susu.core.designsystem.component.appbar.icon.LogoIcon
import com.susu.core.designsystem.component.appbar.icon.SearchIcon
import com.susu.core.designsystem.component.bottomsheet.SusuSelectionBottomSheet
import com.susu.core.designsystem.component.button.FilledButtonColor
import com.susu.core.designsystem.component.button.FilterButton
import com.susu.core.designsystem.component.button.GhostButtonColor
import com.susu.core.designsystem.component.button.SelectedFilterButton
import com.susu.core.designsystem.component.button.SmallButtonStyle
import com.susu.core.designsystem.component.button.SusuFloatingButton
import com.susu.core.designsystem.component.button.SusuGhostButton
import com.susu.core.designsystem.theme.Gray10
import com.susu.core.designsystem.theme.Gray15
import com.susu.core.designsystem.theme.Gray50
import com.susu.core.designsystem.theme.SusuTheme
import com.susu.core.model.Friend
import com.susu.core.ui.extension.OnBottomReached
import com.susu.core.ui.extension.collectWithLifecycle
import com.susu.core.ui.extension.toMoneyFormat
import com.susu.feature.sent.component.SentCard
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.android.awaitFrame
import kotlinx.coroutines.launch
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SentRoute(
    viewModel: SentViewModel = hiltViewModel(),
    deletedFriendId: Long?,
    editedFriendId: Long?,
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
    val scope = rememberCoroutineScope()

    val refreshState = rememberPullToRefreshState(
        positionalThreshold = 100.dp,
    )

    viewModel.sideEffect.collectWithLifecycle { sideEffect ->
        when (sideEffect) {
            SentEffect.NavigateEnvelopeAdd -> navigateSentEnvelopeAdd()
            is SentEffect.NavigateEnvelope -> navigateSentEnvelope(sideEffect.id)
            SentEffect.NavigateEnvelopeSearch -> navigateSentEnvelopeSearch()
            is SentEffect.NavigateEnvelopeFilter -> navigateEnvelopeFilter(sideEffect.filter)
            SentEffect.ScrollToTop -> scope.launch {
                awaitFrame()
                envelopesListState.animateScrollToItem(0)
            }

            is SentEffect.FocusToLastEnvelope -> scope.launch {
                val lastItem = envelopesListState.layoutInfo.visibleItemsInfo.lastOrNull()
                lastItem?.let {
                    envelopesListState.animateScrollBy(
                        it.offset.toFloat(),
                        spring(
                            stiffness = Spring.StiffnessMediumLow,
                        ),
                    )
                }
            }
        }
    }

    LaunchedEffect(key1 = refreshState.isRefreshing) {
        if (refreshState.isRefreshing.not()) return@LaunchedEffect

        viewModel.getEnvelopesList(refresh = true) {
            refreshState.endRefresh()
        }
    }

    LaunchedEffect(key1 = Unit) {
        if (deletedFriendId != null) {
            viewModel.deleteEmptyFriendStatistics(deletedFriendId)
        }
        viewModel.getEnvelopesList(refresh)
        viewModel.filterIfNeed(filter)
        if (editedFriendId != null) {
            viewModel.editFriendStatistics(editedFriendId)
        }
    }

    envelopesListState.OnBottomReached {
        viewModel.getEnvelopesList(refresh = false)
    }

    SentScreen(
        uiState = uiState,
        envelopesListState = envelopesListState,
        refreshState = refreshState,
        padding = padding,
        onClickHistoryShowAll = viewModel::navigateSentEnvelope,
        onClickAddEnvelope = viewModel::navigateSentAdd,
        onClickSearchIcon = viewModel::navigateSentEnvelopeSearch,
        onClickHistory = { expand, friendId ->
            if (expand) {
                viewModel.hideEnvelopesHistoryList(friendId)
            } else { // history 열려 있을 경우 데이터 요청
                viewModel.getEnvelopesHistoryList(friendId)
            }
        },
        onClickFilterButton = viewModel::navigateEnvelopeFilter,
        onClickFriendClose = viewModel::unselectFriend,
        onClickMoneyClose = viewModel::removeMoney,
        onClickAlignButton = viewModel::showAlignBottomSheet,
        onClickAlignBottomSheetItem = viewModel::updateAlignPosition,
        onDismissAlignBottomSheet = viewModel::hideAlignBottomSheet,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SentScreen(
    uiState: SentState = SentState(),
    envelopesListState: LazyListState = rememberLazyListState(),
    refreshState: PullToRefreshState = rememberPullToRefreshState(),
    padding: PaddingValues,
    onClickSearchIcon: () -> Unit = {},
    onClickHistory: (Boolean, Long) -> Unit = { _, _ -> },
    onClickHistoryShowAll: (Long) -> Unit = {},
    onClickAddEnvelope: () -> Unit = {},
    onClickFilterButton: () -> Unit = {},
    onClickFriendClose: (Friend) -> Unit = {},
    onClickMoneyClose: () -> Unit = {},
    onClickAlignButton: () -> Unit = {},
    onClickAlignBottomSheetItem: (Int) -> Unit = {},
    onDismissAlignBottomSheet: () -> Unit = {},
) {
    Surface {
        Box(
            modifier = Modifier
                .background(SusuTheme.colorScheme.background15)
                .padding(padding)
                .fillMaxSize()
                .nestedScroll(refreshState.nestedScrollConnection),
        ) {
            Column {
                Spacer(modifier = Modifier.size(44.dp))

                val state = rememberCollapsingToolbarScaffoldState()
                CollapsingToolbarScaffold(
                    modifier = Modifier.weight(1f),
                    state = state,
                    scrollStrategy = ScrollStrategy.EnterAlways,
                    toolbar = {
                        FilterSection(
                            uiState = uiState,
                            padding = PaddingValues(
                                vertical = SusuTheme.spacing.spacing_m,
                            ),
                            onClickAlignButton = onClickAlignButton,
                            onClickFilterButton = onClickFilterButton,
                            onClickFriendClose = onClickFriendClose,
                            onClickMoneyClose = onClickMoneyClose,
                        )
                    },
                ) {
                    if (uiState.showEmptyEnvelopes) {
                        EmptyView(
                            onClickAddEnvelope = onClickAddEnvelope,
                        )
                    } else {
                        LazyColumn(
                            state = envelopesListState,
                            verticalArrangement = Arrangement.spacedBy(SusuTheme.spacing.spacing_xxs),
                            contentPadding = PaddingValues(
                                start = SusuTheme.spacing.spacing_m,
                                end = SusuTheme.spacing.spacing_m,
                                bottom = SusuTheme.spacing.spacing_m,
                            ),
                        ) {
                            items(
                                items = uiState.envelopesList,
                                key = { it.friend.id },
                            ) {
                                SentCard(
                                    state = it,
                                    onClickHistory = onClickHistory,
                                    onClickHistoryShowAll = onClickHistoryShowAll,
                                )
                            }
                        }
                    }
                }
            }

            SusuDefaultAppBar(
                modifier = Modifier.background(Gray15),
                leftIcon = {
                    Spacer(modifier = Modifier.size(SusuTheme.spacing.spacing_xs))
                    LogoIcon()
                },
                title = stringResource(R.string.sent_screen_appbar_title),
                actions = {
                    SearchIcon(onClickSearchIcon)
                },
            )

            PullToRefreshContainer(
                modifier = Modifier.align(Alignment.TopCenter),
                state = refreshState,
                containerColor = Gray10,
            )

            if (uiState.showAlignBottomSheet) {
                SusuSelectionBottomSheet(
                    onDismissRequest = onDismissAlignBottomSheet,
                    containerHeight = 250.dp,
                    items = EnvelopeAlign.entries.map { stringResource(id = it.stringResId) }.toPersistentList(),
                    selectedItemPosition = uiState.selectedAlignPosition,
                    onClickItem = onClickAlignBottomSheetItem,
                )
            }

            SusuFloatingButton(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(SusuTheme.spacing.spacing_l),
                onClick = onClickAddEnvelope,
            )
        }
    }
}

@Composable
fun FilterSection(
    modifier: Modifier = Modifier,
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

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun SentScreenPreview() {
    SusuTheme {
        SentScreen(padding = PaddingValues(0.dp))
    }
}
