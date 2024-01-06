package com.susu.feature.received.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.susu.core.ui.extension.collectWithLifecycle
import com.susu.feature.received.R
import kotlinx.collections.immutable.PersistentList

@Composable
fun LedgerSearchRoute(
    viewModel: LedgerSearchViewModel = hiltViewModel(),
    popBackStack: () -> Unit,
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    viewModel.sideEffect.collectWithLifecycle { sideEffect ->
        when (sideEffect) {
            LedgerSearchSideEffect.PopBackStack -> popBackStack()
        }
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.getLedgerRecentSearchList()
    }

    LedgerSearchScreen(
        uiState = uiState,
        onClickBackIcon = viewModel::popBackStack,
        onValueChangeSearchBar = viewModel::updateSearch,
        onClickRecentSearchContainer = viewModel::upsertLedgerRecentSearch,
        onClickRecentSearchContainerCloseIcon = viewModel::deleteLedgerRecentSearch,
        onSearchDone = {
            viewModel.upsertLedgerRecentSearch(it)
            viewModel.updateSearch("")
        },
    )
}

@Composable
fun LedgerSearchScreen(
    uiState: LedgerSearchState = LedgerSearchState(),
    onClickBackIcon: () -> Unit = {},
    onValueChangeSearchBar: (String) -> Unit = {},
    onClickRecentSearchContainer: (String) -> Unit = {},
    onClickRecentSearchContainerCloseIcon: (String) -> Unit = {},
    onSearchDone: (String) -> Unit = {}, // TODO REMOVE -> 테스트 용
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
                    value = uiState.search,
                    onValueChange = onValueChangeSearchBar,
                    placeholder = stringResource(R.string.ledger_search_screen_search_placeholder),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            // TODO REMOVE -> 테스트 용
                            onSearchDone(uiState.search)
                        },
                    ),
                )

                if (uiState.searchList.isEmpty()) {
                    RecentSearchEmptyScreen()
                } else {
                    RecentSearchScreen(
                        recentSearchList = uiState.searchList,
                        onClickItem = onClickRecentSearchContainer,
                        onClickCloseIcon = onClickRecentSearchContainerCloseIcon,
                    )
                }
            }
        }
    }
}

@Composable
private fun RecentSearchEmptyScreen() {
    Column(
        modifier = Modifier.fillMaxWidth().padding(top = 136.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(SusuTheme.spacing.spacing_xxxxs),
    ) {
        Text(
            text = stringResource(R.string.ledger_search_screen_empty_recent_search_title),
            style = SusuTheme.typography.title_xs,
            color = Gray80,
        )
        Text(
            text = stringResource(R.string.ledger_search_screen_empty_recent_search_description),
            style = SusuTheme.typography.text_xxs,
            textAlign = TextAlign.Center,
            color = Gray80,
        )
    }
}

@Composable
private fun RecentSearchScreen(
    recentSearchList: PersistentList<String>,
    onClickItem: (String) -> Unit,
    onClickCloseIcon: (String) -> Unit,
) {
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

@Preview
@Composable
fun LedgerSearchScreenPreview() {
    SusuTheme {
        LedgerSearchScreen()
    }
}
