package com.susu.feature.community.community

import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.os.bundleOf
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.firebase.analytics.FirebaseAnalytics
import com.susu.core.designsystem.component.appbar.SusuDefaultAppBar
import com.susu.core.designsystem.component.appbar.icon.LogoIcon
import com.susu.core.designsystem.component.appbar.icon.SearchIcon
import com.susu.core.designsystem.component.button.FilledButtonColor
import com.susu.core.designsystem.component.button.SusuFilledButton
import com.susu.core.designsystem.component.button.SusuFloatingButton
import com.susu.core.designsystem.component.button.XSmallButtonStyle
import com.susu.core.designsystem.theme.Gray10
import com.susu.core.designsystem.theme.Gray20
import com.susu.core.designsystem.theme.Gray30
import com.susu.core.designsystem.theme.Gray40
import com.susu.core.designsystem.theme.Gray50
import com.susu.core.designsystem.theme.Orange60
import com.susu.core.designsystem.theme.SusuTheme
import com.susu.core.model.Category
import com.susu.core.model.Vote
import com.susu.core.ui.DialogToken
import com.susu.core.ui.SnackbarToken
import com.susu.core.ui.extension.OnBottomReached
import com.susu.core.ui.extension.collectWithLifecycle
import com.susu.core.ui.extension.susuClickable
import com.susu.feature.community.R
import com.susu.feature.community.community.component.MostPopularVoteCard
import com.susu.feature.community.community.component.VoteCard
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommunityRoute(
    padding: PaddingValues,
    vote: String?,
    needRefresh: Boolean,
    toDeleteVoteId: Long?,
    toUpdateVote: String?,
    viewModel: CommunityViewModel = hiltViewModel(),
    navigateVoteAdd: () -> Unit,
    navigateVoteSearch: () -> Unit,
    navigateVoteDetail: (Long) -> Unit,
    onShowDialog: (DialogToken) -> Unit,
    handleException: (Throwable, () -> Unit) -> Unit,
    onShowSnackbar: (SnackbarToken) -> Unit,
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val refreshState = rememberPullToRefreshState(
        positionalThreshold = 100.dp,
    )

    viewModel.sideEffect.collectWithLifecycle { sideEffect ->
        when (sideEffect) {
            is CommunitySideEffect.HandleException -> handleException(sideEffect.throwable, sideEffect.retry)
            CommunitySideEffect.NavigateVoteAdd -> navigateVoteAdd()
            is CommunitySideEffect.NavigateVoteDetail -> navigateVoteDetail(sideEffect.voteId)
            CommunitySideEffect.NavigateVoteSearch -> navigateVoteSearch()
            is CommunitySideEffect.ShowReportDialog -> onShowDialog(
                DialogToken(
                    title = context.getString(R.string.dialog_report_title),
                    text = context.getString(R.string.dialog_report_body),
                    confirmText = context.getString(R.string.dialog_report_confirm_text),
                    dismissText = context.getString(R.string.dialog_report_dismiss_text),
                    checkboxText = context.getString(R.string.dialog_report_checkbox_text),
                    onConfirmRequest = sideEffect.onConfirmRequest,
                    onCheckedAction = sideEffect.onCheckedAction,
                ),
            )

            is CommunitySideEffect.ShowSnackbar -> onShowSnackbar(SnackbarToken(message = sideEffect.message))
            CommunitySideEffect.LogSearchIconClickEvent -> scope.launch {
                FirebaseAnalytics.getInstance(context).logEvent(
                    FirebaseAnalytics.Event.SELECT_CONTENT,
                    bundleOf(
                        FirebaseAnalytics.Param.CONTENT_TYPE to "community_screen_search_icon",
                    ),
                )
            }

            CommunitySideEffect.LogAlignPopularVoteClickEvent -> scope.launch {
                FirebaseAnalytics.getInstance(context).logEvent(
                    FirebaseAnalytics.Event.SELECT_CONTENT,
                    bundleOf(
                        FirebaseAnalytics.Param.CONTENT_TYPE to "community_screen_align_popular_vote",
                    ),
                )
            }
            is CommunitySideEffect.LogCategoryClickEvent -> scope.launch {
                FirebaseAnalytics.getInstance(context).logEvent(
                    FirebaseAnalytics.Event.SELECT_CONTENT,
                    bundleOf(
                        FirebaseAnalytics.Param.CONTENT_TYPE to "community_screen_category_${sideEffect.name}",
                    ),
                )
            }
            CommunitySideEffect.LogPopularVoteClickEvent -> scope.launch {
                FirebaseAnalytics.getInstance(context).logEvent(
                    FirebaseAnalytics.Event.SELECT_CONTENT,
                    bundleOf(
                        FirebaseAnalytics.Param.CONTENT_TYPE to "community_screen_popular_vote",
                    ),
                )
            }
            CommunitySideEffect.LogShowMyVoteClickEvent -> scope.launch {
                FirebaseAnalytics.getInstance(context).logEvent(
                    FirebaseAnalytics.Event.SELECT_CONTENT,
                    bundleOf(
                        FirebaseAnalytics.Param.CONTENT_TYPE to "community_screen_show_my_vote",
                    ),
                )
            }
        }
    }

    var currentTime by remember {
        mutableStateOf(LocalDateTime.now())
    }

    LaunchedEffect(key1 = Unit) {
        while (true) {
            delay(60 * 1000L)
            currentTime = currentTime.plusMinutes(1)
        }
    }

    val voteListState = rememberLazyListState()

    LaunchedEffect(key1 = refreshState.isRefreshing) {
        if (refreshState.isRefreshing.not()) return@LaunchedEffect

        viewModel.refreshData {
            refreshState.endRefresh()
        }
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.initData()
        viewModel.getCategoryConfig()
        viewModel.getPopularVoteList()
        viewModel.addVoteIfNeed(vote)
        viewModel.updateVoteIfNeed(toUpdateVote)
        viewModel.deleteVoteIfNeed(toDeleteVoteId)
        viewModel.needRefreshIfNeed(needRefresh)
    }

    voteListState.OnBottomReached(minItemsCount = 4) {
        viewModel.getVoteList()
    }

    CommunityScreen(
        padding = padding,
        refreshState = refreshState,
        uiState = uiState,
        currentTime = currentTime,
        voteListState = voteListState,
        onClickFloatingButton = viewModel::navigateVoteAdd,
        onClickCategory = { category ->
            viewModel.logCategoryClickEvent(category?.name ?: "")
            viewModel.selectCategory(category)
        },
        onClickShowMine = {
            viewModel.logShowMyVoteClickEvent()
            viewModel.toggleShowMyVote()
        },
        onClickShowVotePopular = {
            viewModel.logAlignPopularVoteClickEvent()
            viewModel.toggleShowVotePopular()
        },
        onClickVote = viewModel::navigateVoteDetail,
        onClickPopularVote = {
            viewModel.logPopularVoteClickEvent()
            viewModel.navigateVoteDetail(it)
        },
        onClickSearchIcon = {
            viewModel.navigateVoteSearch()
            viewModel.logSearchIconClickEvent()
        },
        onClickReport = viewModel::showReportDialog,
    )
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun CommunityScreen(
    padding: PaddingValues,
    uiState: CommunityState = CommunityState(),
    refreshState: PullToRefreshState = rememberPullToRefreshState(),
    currentTime: LocalDateTime = LocalDateTime.now(),
    voteListState: LazyListState = rememberLazyListState(),
    onClickSearchIcon: () -> Unit = {},
    onClickFloatingButton: () -> Unit = {},
    onClickVote: (Long) -> Unit = {},
    onClickPopularVote: (Long) -> Unit = {},
    onClickCategory: (Category?) -> Unit = {},
    onClickShowVotePopular: () -> Unit = {},
    onClickShowMine: () -> Unit = {},
    onClickReport: (Vote) -> Unit = {},
) {
    Box(
        modifier = Modifier
            .padding(padding)
            .fillMaxSize()
            .nestedScroll(refreshState.nestedScrollConnection),
    ) {
        Column(
            modifier = Modifier
                .background(SusuTheme.colorScheme.background10),
        ) {
            SusuDefaultAppBar(
                modifier = Modifier.padding(horizontal = SusuTheme.spacing.spacing_xs),
                leftIcon = {
                    LogoIcon()
                },
                title = stringResource(R.string.community_screen_title),
                actions = {
                    Row {
                        SearchIcon(onClickSearchIcon)
                    }
                },
            )

            LazyColumn(
                state = voteListState,
                contentPadding = PaddingValues(vertical = SusuTheme.spacing.spacing_m),
            ) {
                item {
                    Column {
                        Text(
                            modifier = Modifier.padding(start = SusuTheme.spacing.spacing_m),
                            text = stringResource(R.string.community_screen_most_popular_vote),
                            style = SusuTheme.typography.title_xxs,
                        )

                        Spacer(modifier = Modifier.size(SusuTheme.spacing.spacing_xxs))

                        LazyRow(
                            contentPadding = PaddingValues(horizontal = SusuTheme.spacing.spacing_m),
                            horizontalArrangement = Arrangement.spacedBy(SusuTheme.spacing.spacing_m),
                        ) {
                            items(
                                items = uiState.popularVoteList,
                                key = { it.id },
                            ) { vote ->
                                MostPopularVoteCard(vote, onClick = { onClickPopularVote(vote.id) })
                            }
                        }
                    }
                }

                item {
                    Spacer(
                        modifier = Modifier
                            .size(SusuTheme.spacing.spacing_m),
                    )
                    HorizontalDivider(
                        thickness = SusuTheme.spacing.spacing_xxs,
                        color = Gray20,
                    )
                }

                stickyHeader {
                    Column(
                        modifier = Modifier
                            .background(Gray10)
                            .padding(
                                top = SusuTheme.spacing.spacing_m,
                                start = SusuTheme.spacing.spacing_m,
                                end = SusuTheme.spacing.spacing_m,
                                bottom = SusuTheme.spacing.spacing_xxs,
                            ),
                        verticalArrangement = Arrangement.spacedBy(SusuTheme.spacing.spacing_xl),
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(SusuTheme.spacing.spacing_xxxxs),
                        ) {
                            SusuFilledButton(
                                color = FilledButtonColor.Black,
                                style = XSmallButtonStyle.height28,
                                text = stringResource(com.susu.core.ui.R.string.word_all),
                                isActive = uiState.selectedCategory == null,
                                onClick = { onClickCategory(null) },
                            )

                            uiState.categoryConfigList.forEach { category ->
                                SusuFilledButton(
                                    color = FilledButtonColor.Black,
                                    style = XSmallButtonStyle.height28,
                                    text = category.name,
                                    isActive = uiState.selectedCategory == category,
                                    onClick = { onClickCategory(category) },
                                )
                            }
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            Row(
                                modifier = Modifier.susuClickable(
                                    rippleEnabled = false,
                                    onClick = onClickShowVotePopular,
                                ),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(SusuTheme.spacing.spacing_xxs),
                            ) {
                                Box(
                                    modifier = Modifier
                                        .clip(CircleShape)
                                        .size(6.dp)
                                        .background(if (uiState.isCheckedVotePopular) Orange60 else Gray30),
                                )

                                Text(
                                    text = stringResource(R.string.community_screen_vote_align_high),
                                    style = SusuTheme.typography.title_xxxs,
                                    color = if (uiState.isCheckedVotePopular) Orange60 else Gray40,
                                )
                            }

                            Row(
                                modifier = Modifier.susuClickable(
                                    rippleEnabled = false,
                                    onClick = onClickShowMine,
                                ),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(SusuTheme.spacing.spacing_xxxxs),
                            ) {
                                Icon(
                                    painter = painterResource(
                                        id = if (uiState.isCheckShowMine) R.drawable.ic_vote_check else R.drawable.ic_uncheck,
                                    ),
                                    contentDescription = null,
                                    tint = if (uiState.isCheckShowMine) Orange60 else Gray40,
                                )

                                Text(
                                    text = stringResource(R.string.community_screen_show_my_article),
                                    style = SusuTheme.typography.title_xxxs,
                                    color = if (uiState.isCheckShowMine) Orange60 else Gray40,
                                )
                            }
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.size(SusuTheme.spacing.spacing_s))
                }

                items(
                    items = uiState.voteList,
                    key = { it.id },
                ) { vote ->
                    VoteCard(
                        vote = vote,
                        currentTime = currentTime,
                        onClick = { onClickVote(vote.id) },
                        onClickReport = onClickReport,
                    )
                }
            }

            if (uiState.voteList.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = stringResource(R.string.community_screen_empty_vote),
                        style = SusuTheme.typography.text_s,
                        color = Gray50,
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
            imageResId = R.drawable.ic_vote_add,
            onClick = onClickFloatingButton,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun CommunityScreenPreview() {
    SusuTheme {
        CommunityScreen(padding = PaddingValues(0.dp))
    }
}
