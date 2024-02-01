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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
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
import com.susu.core.ui.extension.OnBottomReached
import com.susu.core.ui.extension.collectWithLifecycle
import com.susu.core.ui.extension.susuClickable
import com.susu.feature.community.R
import com.susu.feature.community.community.component.MostPopularVoteCard
import com.susu.feature.community.community.component.VoteCard
import kotlinx.coroutines.delay
import java.time.LocalDateTime

@Composable
fun CommunityRoute(
    padding: PaddingValues,
    vote: String?,
    toDeleteVoteId: Long?,
    toUpdateVote: String?,
    viewModel: CommunityViewModel = hiltViewModel(),
    navigateVoteAdd: () -> Unit,
    navigateVoteSearch: () -> Unit,
    navigateVoteDetail: (Long) -> Unit,
    onShowDialog: (DialogToken) -> Unit,
    handleException: (Throwable, () -> Unit) -> Unit,
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    val context = LocalContext.current
    viewModel.sideEffect.collectWithLifecycle { sideEffect ->
        when (sideEffect) {
            is CommunitySideEffect.HandleException -> handleException(sideEffect.throwable, sideEffect.retry)
            CommunitySideEffect.NavigateVoteAdd -> navigateVoteAdd()
            is CommunitySideEffect.NavigateVoteDetail -> navigateVoteDetail(sideEffect.voteId)
            CommunitySideEffect.NavigateVoteSearch -> navigateVoteSearch()
            is CommunitySideEffect.ShowDeleteDialog -> onShowDialog(
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

    LaunchedEffect(key1 = Unit) {
        viewModel.initData()
        viewModel.getCategoryConfig()
        viewModel.getPopularVoteList()
        viewModel.addVoteIfNeed(vote)
        viewModel.updateVoteIfNeed(toUpdateVote)
        viewModel.deleteVoteIfNeed(toDeleteVoteId)
    }

    voteListState.OnBottomReached(minItemsCount = 4) {
        viewModel.getVoteList()
    }

    CommunityScreen(
        padding = padding,
        uiState = uiState,
        currentTime = currentTime,
        voteListState = voteListState,
        onClickFloatingButton = viewModel::navigateVoteAdd,
        onClickCategory = viewModel::selectCategory,
        onClickShowMine = viewModel::toggleShowMyVote,
        onClickShowVotePopular = viewModel::toggleShowVotePopular,
        onClickVote = viewModel::navigateVoteDetail,
        onClickSearchIcon = viewModel::navigateVoteSearch,
        onClickReport = viewModel::showReportDialog,
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CommunityScreen(
    padding: PaddingValues,
    uiState: CommunityState = CommunityState(),
    currentTime: LocalDateTime = LocalDateTime.now(),
    voteListState: LazyListState = rememberLazyListState(),
    onClickSearchIcon: () -> Unit = {},
    onClickFloatingButton: () -> Unit = {},
    onClickVote: (Long) -> Unit = {},
    onClickCategory: (Category?) -> Unit = {},
    onClickShowVotePopular: () -> Unit = {},
    onClickShowMine: () -> Unit = {},
    onClickReport: (Vote) -> Unit = {},
) {
    Box(
        modifier = Modifier
            .padding(padding)
            .fillMaxSize(),
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
                                MostPopularVoteCard(vote, onClick = { onClickVote(vote.id) })
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

        SusuFloatingButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(SusuTheme.spacing.spacing_l),
            imageResId = R.drawable.ic_vote_add,
            onClick = onClickFloatingButton,
        )
    }
}

@Preview
@Composable
fun CommunityScreenPreview() {
    SusuTheme {
        CommunityScreen(padding = PaddingValues(0.dp))
    }
}
