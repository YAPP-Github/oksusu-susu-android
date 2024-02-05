package com.susu.feature.envelopesearch

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
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
import com.susu.core.model.Envelope
import com.susu.core.ui.extension.collectWithLifecycle
import com.susu.feature.sent.R
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce

@OptIn(FlowPreview::class)
@Composable
fun SentEnvelopeSearchRoute(
    viewModel: EnvelopeSearchViewModel = hiltViewModel(),
    navigateSentEnvelopeDetail: (Long) -> Unit,
    popBackStack: () -> Unit,
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value

    viewModel.sideEffect.collectWithLifecycle { sideEffect ->
        when (sideEffect) {
            EnvelopeSearchEffect.FocusClear -> {}
            is EnvelopeSearchEffect.NavigateEnvelopDetail -> navigateSentEnvelopeDetail(sideEffect.envelopeId)
            EnvelopeSearchEffect.PopBackStack -> popBackStack()
        }
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.getEnvelopeRecentSearchList()
    }

    LaunchedEffect(key1 = uiState.searchKeyword) {
        snapshotFlow { uiState.searchKeyword }
            .debounce(100L)
            .collect(viewModel::getEnvelopeList)
    }

    SentEnvelopeSearchScreen(
        uiState = uiState,
        onSearchKeywordUpdated = viewModel::updateSearchKeyword,
        onClickClearIcon = { viewModel.updateSearchKeyword("") },
        onSelectRecentSearch = {
            viewModel.upsertEnvelopeRecentSearch(it)
            viewModel.updateSearchKeyword(it)
        },
        onDeleteRecentSearch = viewModel::deleteEnvelopeRecentSearch,
        popBackStack = popBackStack,
        onClickEnvelope = { viewModel.navigateToEnvelopeDetail(it.id) },
    )
}

@Composable
fun SentEnvelopeSearchScreen(
    uiState: EnvelopeSearchState = EnvelopeSearchState(),
    onSearchKeywordUpdated: (String) -> Unit = {},
    onClickClearIcon: () -> Unit = {},
    onSelectRecentSearch: (String) -> Unit = {},
    onDeleteRecentSearch: (String) -> Unit = {},
    onClickEnvelope: (Envelope) -> Unit = {},
    popBackStack: () -> Unit = {},
) {
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        SusuDefaultAppBar(
            leftIcon = {
                BackIcon(onClick = popBackStack)
            },
        )
        Spacer(modifier = Modifier.height(SusuTheme.spacing.spacing_xxs))
        SusuSearchBar(
            modifier = Modifier.padding(
                horizontal = SusuTheme.spacing.spacing_m,
                vertical = SusuTheme.spacing.spacing_xxs,
            ),
            value = uiState.searchKeyword,
            placeholder = stringResource(R.string.sent_envelope_search_search_title),
            onValueChange = onSearchKeywordUpdated,
            onClickClearIcon = onClickClearIcon,
        )
        if (uiState.searchKeyword.isEmpty()) {
            if (uiState.recentSearchKeywordList.isEmpty()) {
                EmptyRecentSearch(modifier = Modifier.fillMaxWidth())
            } else {
                Spacer(modifier = Modifier.height(SusuTheme.spacing.spacing_xxl))
                Text(
                    modifier = Modifier.padding(horizontal = SusuTheme.spacing.spacing_m),
                    text = stringResource(R.string.sent_envelope_search_recent_search),
                    style = SusuTheme.typography.title_xxs,
                    color = Gray60,
                )
                Spacer(modifier = Modifier.height(SusuTheme.spacing.spacing_m))
                RecentSearchColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = SusuTheme.spacing.spacing_m),
                    recentSearchList = uiState.recentSearchKeywordList,
                    onClickItem = onSelectRecentSearch,
                    onClickClearIcon = onDeleteRecentSearch,
                )
            }
        } else {
            Spacer(modifier = Modifier.height(SusuTheme.spacing.spacing_xxl))
            Text(
                modifier = Modifier.padding(horizontal = SusuTheme.spacing.spacing_m),
                text = stringResource(R.string.sent_envelope_search_search_result),
                style = SusuTheme.typography.title_xxs,
                color = Gray60,
            )
            if (uiState.envelopeList.isEmpty()) {
                EmptySearchEnvelope(modifier = Modifier.fillMaxWidth())
            } else {
                Spacer(modifier = Modifier.height(SusuTheme.spacing.spacing_m))
                SearchEnvelopeColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = SusuTheme.spacing.spacing_m),
                    searchResult = uiState.envelopeList,
                    onClickItem = onClickEnvelope,
                )
            }
        }
    }
}

@Composable
fun EmptyRecentSearch(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        Spacer(modifier = Modifier.height(136.dp))
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = stringResource(R.string.sent_envelope_search_empty_search_title),
            style = SusuTheme.typography.title_xs,
            color = Gray80,
        )
        Spacer(modifier = Modifier.height(SusuTheme.spacing.spacing_xxxxs))
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = stringResource(R.string.sent_envelope_search_description),
            style = SusuTheme.typography.text_xxs,
            color = Gray80,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
fun RecentSearchColumn(
    modifier: Modifier = Modifier,
    recentSearchList: PersistentList<String> = persistentListOf(),
    onClickItem: (String) -> Unit = {},
    onClickClearIcon: (String) -> Unit = {},
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(SusuTheme.spacing.spacing_m),
    ) {
        recentSearchList.forEach { keyword ->
            SusuRecentSearchContainer(
                text = keyword,
                onClick = { onClickItem(keyword) },
                onClickCloseIcon = { onClickClearIcon(keyword) },
            )
        }
    }
}

@Composable
fun EmptySearchEnvelope(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        Spacer(modifier = Modifier.height(136.dp))
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = stringResource(R.string.sent_envelope_search_empty_result_title),
            style = SusuTheme.typography.title_xs,
            color = Gray80,
        )
        Spacer(modifier = Modifier.height(SusuTheme.spacing.spacing_xxxxs))
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = stringResource(R.string.sent_envelope_search_description),
            style = SusuTheme.typography.text_xxs,
            color = Gray80,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
fun SearchEnvelopeColumn(
    modifier: Modifier = Modifier,
    searchResult: PersistentList<Envelope> = persistentListOf(),
    onClickItem: (Envelope) -> Unit = {},
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(SusuTheme.spacing.spacing_m),
    ) {
        searchResult.forEach { envelope ->
            SusuRecentSearchContainer(
                typeIconId = R.drawable.ic_envelope,
                text = stringResource(R.string.sent_envelope_search_who_envelope, envelope.friend.name),
                onClick = { onClickItem(envelope) },
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun SentEnvelopeSearchScreenPreview() {
    SusuTheme {
        SentEnvelopeSearchScreen()
    }
}
