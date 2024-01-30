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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.susu.core.designsystem.component.appbar.SusuDefaultAppBar
import com.susu.core.designsystem.component.appbar.icon.BackIcon
import com.susu.core.designsystem.component.container.SusuRecentSearchContainer
import com.susu.core.designsystem.component.searchbar.SusuSearchBar
import com.susu.core.designsystem.theme.Gray60
import com.susu.core.designsystem.theme.Gray80
import com.susu.core.designsystem.theme.SusuTheme
import com.susu.core.model.Envelope
import com.susu.core.model.Friend
import com.susu.feature.sent.R
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun SentEnvelopeSearchRoute() {
}

@Composable
fun SentEnvelopeSearchScreen(
    searchText: String = "",
    recentSearch: PersistentList<String> = persistentListOf(),
    searchResult: PersistentList<Envelope> = persistentListOf(),
    onSelectRecentSearch: (Int) -> Unit = {},
    onDeleteRecentSearch: (Int) -> Unit = {},
    onClickEnvelope: (Envelope) -> Unit = {},
) {
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        SusuDefaultAppBar(
            leftIcon = {
                BackIcon()
            },
        )
        Spacer(modifier = Modifier.height(SusuTheme.spacing.spacing_xxs))
        SusuSearchBar(
            modifier = Modifier.padding(
                horizontal = SusuTheme.spacing.spacing_m,
                vertical = SusuTheme.spacing.spacing_xxs,
            ),
            value = searchText,
            placeholder = stringResource(R.string.sent_envelope_search_search_title),
        )
        if (searchText.isEmpty()) {
            if (recentSearch.isEmpty()) {
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
                    modifier = Modifier.fillMaxWidth().padding(horizontal = SusuTheme.spacing.spacing_m),
                    recentSearchList = recentSearch,
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
            if (searchResult.isEmpty()) {
                EmptySearchEnvelope(modifier = Modifier.fillMaxWidth())
            } else {
                Spacer(modifier = Modifier.height(SusuTheme.spacing.spacing_m))
                SearchEnvelopeColumn(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = SusuTheme.spacing.spacing_m),
                    searchResult = searchResult,
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
    onClickItem: (Int) -> Unit = {},
    onClickClearIcon: (Int) -> Unit = {},
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(SusuTheme.spacing.spacing_m),
    ) {
        recentSearchList.forEachIndexed { index, keyword ->
            SusuRecentSearchContainer(
                text = keyword,
                onClick = { onClickItem(index) },
                onClickCloseIcon = { onClickClearIcon(index) },
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
        SentEnvelopeSearchScreen(
            searchText = "d",
            searchResult = persistentListOf(Envelope(friend = Friend(name = "김수수"))),
        )
    }
}
