package com.susu.feature.envelopeadd.content.name

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.susu.core.designsystem.component.textfield.SusuBasicTextField
import com.susu.core.designsystem.theme.Gray100
import com.susu.core.designsystem.theme.Gray40
import com.susu.core.designsystem.theme.SusuTheme
import com.susu.core.model.FriendSearch
import com.susu.core.ui.SnackbarToken
import com.susu.core.ui.extension.collectWithLifecycle
import com.susu.feature.envelopeadd.content.component.FriendListItem
import com.susu.feature.sent.R
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.android.awaitFrame
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
@Composable
fun NameContentRoute(
    viewModel: NameViewModel = hiltViewModel(),
    updateParentName: (String) -> Unit,
    updateParentFriendId: (Long?) -> Unit,
    onShowSnackbar: (SnackbarToken) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }
    val scope = rememberCoroutineScope()

    viewModel.sideEffect.collectWithLifecycle { sideEffect ->
        when (sideEffect) {
            NameEffect.FocusClear -> focusManager.clearFocus()
            is NameEffect.UpdateParentFriendId -> updateParentFriendId(sideEffect.friendId)
            is NameEffect.UpdateParentName -> updateParentName(sideEffect.name)
            NameEffect.ShowNotValidSnackbar -> onShowSnackbar(
                SnackbarToken(
                    message = context.getString(R.string.sent_snackbar_name_validation),
                    extraPadding = PaddingValues(bottom = 60.dp),
                ),
            )

            NameEffect.ShowKeyboard -> scope.launch {
                awaitFrame()
                focusRequester.requestFocus()
            }
        }
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.showKeyboardIfTextEmpty()
    }

    LaunchedEffect(key1 = Unit) {
        updateParentName(uiState.name)
    }

    LaunchedEffect(key1 = uiState.name) {
        snapshotFlow { uiState.name }
            .debounce(100L)
            .collect(viewModel::getFriendList)
    }

    NameContent(
        uiState = uiState,
        focusRequester = focusRequester,
        onTextChangeName = viewModel::updateName,
        onClickFriendItem = viewModel::selectFriend,
    )
}

@Composable
fun NameContent(
    uiState: NameState = NameState(),
    padding: PaddingValues = PaddingValues(
        horizontal = SusuTheme.spacing.spacing_m,
        vertical = SusuTheme.spacing.spacing_xl,
    ),
    focusRequester: FocusRequester = remember { FocusRequester() },
    onTextChangeName: (String) -> Unit = {},
    onClickFriendItem: (FriendSearch) -> Unit = {},
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
            onTextChange = onTextChangeName,
            placeholder = stringResource(id = R.string.sent_envelope_add_name_placeholder),
            placeholderColor = Gray40,
            modifier = Modifier.fillMaxWidth().focusRequester(focusRequester),
        )
        Spacer(modifier = Modifier.size(SusuTheme.spacing.spacing_xl))

        if (uiState.friendList.isNotEmpty() && uiState.isSelectedFriend.not()) {
            LazyColumn(
                modifier = Modifier.height(208.dp),
            ) {
                items(
                    items = uiState.friendList,
                    key = { it.friend.id },
                ) {
                    FriendListItem(
                        name = it.friend.name,
                        relationship = it.relationship.customRelation ?: it.relationship.relation,
                        category = it.recentEnvelope?.category,
                        visitedAt = it.recentEnvelope?.handedOverAt,
                        onClick = { onClickFriendItem(it) },
                    )
                }
            }
        }
    }
}
