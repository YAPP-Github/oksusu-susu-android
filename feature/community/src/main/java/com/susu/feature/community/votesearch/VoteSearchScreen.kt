package com.susu.feature.community.votesearch

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.susu.core.designsystem.component.appbar.SusuDefaultAppBar
import com.susu.core.designsystem.component.appbar.icon.BackIcon
import com.susu.core.designsystem.component.container.SusuRecentSearchContainer
import com.susu.core.designsystem.component.searchbar.SusuSearchBar
import com.susu.core.designsystem.theme.Gray60
import com.susu.core.designsystem.theme.Gray80
import com.susu.core.designsystem.theme.SusuTheme
import com.susu.core.model.Vote
import com.susu.core.ui.extension.collectWithLifecycle
import com.susu.feature.community.R
import kotlinx.collections.immutable.PersistentList
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce

@OptIn(FlowPreview::class)
@Composable
fun VoteSearchRoute(
    viewModel: VoteSearchViewModel = hiltViewModel(),
    popBackStack: () -> Unit,
    navigateVoteDetail: (Vote) -> Unit,
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    viewModel.sideEffect.collectWithLifecycle { sideEffect ->
        when (sideEffect) {
            VoteSearchSideEffect.PopBackStack -> popBackStack()
            is VoteSearchSideEffect.NavigateVoteDetail -> navigateVoteDetail(sideEffect.vote)
            VoteSearchSideEffect.FocusClear -> focusManager.clearFocus()
        }
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.getVoteRecentSearchList()
    }

    LaunchedEffect(key1 = uiState.searchKeyword) {
        snapshotFlow { uiState.searchKeyword }
            .debounce(100L)
            .collect(viewModel::getVoteList)
    }

    VoteSearchScreen(
        uiState = uiState,
        focusRequester = focusRequester,
        onClickBackIcon = viewModel::popBackStack,
        onValueChangeSearchBar = viewModel::updateSearch,
        onClickSearchClearIcon = { viewModel.updateSearch("") },
        onClickRecentSearchContainer = { search ->
            viewModel.clearFocus()
            viewModel.hideSearchResultEmpty()
            viewModel.updateSearch(search)
            viewModel.upsertVoteRecentSearch(search)
        },
        onClickRecentSearchContainerCloseIcon = viewModel::deleteVoteRecentSearch,
        onClickSearchResultContainer = { vote ->
            viewModel.upsertVoteRecentSearch(vote.content)
            viewModel.navigateVoteDetail(vote)
        },
    )
}

@Composable
fun VoteSearchScreen(
    uiState: VoteSearchState = VoteSearchState(),
    focusRequester: FocusRequester = remember { FocusRequester() },
    onClickBackIcon: () -> Unit = {},
    onClickSearchClearIcon: () -> Unit = {},
    onValueChangeSearchBar: (String) -> Unit = {},
    onClickRecentSearchContainer: (String) -> Unit = {},
    onClickRecentSearchContainerCloseIcon: (String) -> Unit = {},
    onClickSearchResultContainer: (Vote) -> Unit = {},
) {
    Box(
        modifier = Modifier
            .background(SusuTheme.colorScheme.background10)
            .fillMaxSize(),
    ) {
        Column {
            SusuDefaultAppBar(
                leftIcon = {
                    BackIcon(onClickBackIcon)
                },
            )

            Column(
                modifier = Modifier.padding(
                    top = SusuTheme.spacing.spacing_xxs,
                    start = SusuTheme.spacing.spacing_m,
                    end = SusuTheme.spacing.spacing_m,
                ),
            ) {
                SusuSearchBar(
                    modifier = Modifier.focusRequester(focusRequester),
                    value = uiState.searchKeyword,
                    onValueChange = onValueChangeSearchBar,
                    onClickClearIcon = onClickSearchClearIcon,
                    placeholder = "찾고 싶은 투표를 검색해보세요",
                )

                if (uiState.searchKeyword.isEmpty()) {
                    RecentSearchColumn(
                        recentSearchList = uiState.recentSearchKeywordList,
                        onClickItem = onClickRecentSearchContainer,
                        onClickCloseIcon = onClickRecentSearchContainerCloseIcon,
                    )
                } else {
                    SearchResultColumn(
                        showSearchResultEmpty = uiState.showSearchResultEmpty,
                        voteList = uiState.voteList,
                        onClickItem = onClickSearchResultContainer,
                    )
                }
            }
        }
    }
}

@Composable
private fun ResultEmptyColumn(
    title: String,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 136.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(SusuTheme.spacing.spacing_xxxxs),
    ) {
        Text(
            text = title,
            style = SusuTheme.typography.title_xs,
            color = Gray80,
        )
        Text(
            text = "궁금하신 것들의 키워드를\n검색해볼 수 있어요",
            style = SusuTheme.typography.text_xxs,
            textAlign = TextAlign.Center,
            color = Gray80,
        )
    }
}

@Composable
private fun RecentSearchColumn(
    recentSearchList: PersistentList<String>,
    onClickItem: (String) -> Unit,
    onClickCloseIcon: (String) -> Unit,
) {
    if (recentSearchList.isEmpty()) {
        ResultEmptyColumn(
            title = "어떤 투표를 찾아드릴까요?",
        )
    } else {
        Column(
            modifier = Modifier.padding(top = SusuTheme.spacing.spacing_xxl),
            verticalArrangement = Arrangement.spacedBy(SusuTheme.spacing.spacing_m),
        ) {
            Text(
                text = stringResource(com.susu.core.ui.R.string.word_recent_search),
                style = SusuTheme.typography.title_xxs,
                color = Gray60,
            )
            recentSearchList.forEach { name ->
                SusuRecentSearchContainer(
                    text = name,
                    onClick = { onClickItem(name) },
                    onClickCloseIcon = { onClickCloseIcon(name) },
                )
            }
        }
    }
}

@Composable
private fun SearchResultColumn(
    showSearchResultEmpty: Boolean,
    voteList: PersistentList<Vote>,
    onClickItem: (Vote) -> Unit,
) {
    if (showSearchResultEmpty) {
        ResultEmptyColumn(
            title = "원하는 검색 결과가 없나요?",
        )
    } else {
        Column(
            modifier = Modifier.padding(top = SusuTheme.spacing.spacing_xxl),
            verticalArrangement = Arrangement.spacedBy(SusuTheme.spacing.spacing_m),
        ) {
            Text(
                text = stringResource(com.susu.core.ui.R.string.word_search_result),
                style = SusuTheme.typography.title_xxs,
                color = Gray60,
            )
            voteList.forEach { vote ->
                SusuRecentSearchContainer(
                    typeIconId = R.drawable.ic_vote,
                    text = vote.content,
                    onClick = { onClickItem(vote) },
                )
            }
        }
    }
}

@Preview
@Composable
fun VoteSearchScreenPreview() {
    SusuTheme {
        VoteSearchScreen()
    }
}
