package com.susu.feature.envelopeadd.content.name

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.susu.core.designsystem.component.screen.LoadingScreen
import com.susu.core.designsystem.component.textfield.SusuBasicTextField
import com.susu.core.designsystem.theme.Gray100
import com.susu.core.designsystem.theme.Gray40
import com.susu.core.designsystem.theme.SusuTheme
import com.susu.core.ui.extension.collectWithLifecycle
import com.susu.feature.envelopeadd.content.component.FriendListItem
import com.susu.feature.sent.R

@Composable
fun NameContentRoute(
    viewModel: NameViewModel = hiltViewModel(),
    updateParentName: (String) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    viewModel.sideEffect.collectWithLifecycle { sideEffect ->
        when (sideEffect) {
            is NameEffect.SearchFriendByName -> viewModel.searchFriend()
            is NameEffect.UpdateParentName -> {
                updateParentName(sideEffect.name)
            }
        }
    }

    LaunchedEffect(key1 = Unit) {
        updateParentName(uiState.name)
    }

    // TODO: 친구 검색 UI 검증용. 서버 연동 후 삭제 바람
    LaunchedEffect(key1 = uiState.name) {
        if (uiState.name == "김") {
            viewModel.mockSearch()
        }
    }

    NameContent(
        uiState = uiState,
        onNameTextChanged = viewModel::updateName,
        onFriendSelect = viewModel::selectName,
    )
}

@Composable
fun NameContent(
    uiState: NameState = NameState(),
    padding: PaddingValues = PaddingValues(
        horizontal = SusuTheme.spacing.spacing_m,
        vertical = SusuTheme.spacing.spacing_xl,
    ),
    onNameTextChanged: (String) -> Unit = {},
    onFriendSelect: (String) -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
    ) {
        Text(
            text = stringResource(id = R.string.sent_envelope_add_name_title),
            style = SusuTheme.typography.title_m,
            color = Gray100,
        )
        Spacer(
            modifier = Modifier
                .size(SusuTheme.spacing.spacing_m),
        )
        SusuBasicTextField(
            text = uiState.name,
            onTextChange = onNameTextChanged,
            placeholder = stringResource(id = R.string.sent_envelope_add_name_placeholder),
            placeholderColor = Gray40,
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.size(SusuTheme.spacing.spacing_xl))

        if (uiState.isSearching) {
            LoadingScreen(modifier = Modifier.weight(1f))
        } else if (uiState.searchedFriends.isNotEmpty()) {
            // TODO: 친구 목록 서버 연동
            LazyColumn {
                items(uiState.searchedFriends) { friend ->
                    FriendListItem(friend, onClick = onFriendSelect)
                }
            }
        }
    }
}
