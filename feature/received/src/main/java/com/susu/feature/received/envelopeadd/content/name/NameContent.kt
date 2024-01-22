package com.susu.feature.received.envelopeadd.content.name

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.susu.core.designsystem.component.textfield.SusuBasicTextField
import com.susu.core.designsystem.theme.Gray100
import com.susu.core.designsystem.theme.Gray40
import com.susu.core.designsystem.theme.SusuTheme
import com.susu.core.ui.extension.collectWithLifecycle
import com.susu.feature.received.R
import com.susu.feature.received.envelopeadd.content.component.FriendListItem
import com.susu.feature.received.envelopeadd.content.money.MoneySideEffect
import com.susu.feature.received.envelopeadd.content.money.MoneyState

@Composable
fun NameContentRoute(
    viewModel: NameViewModel = hiltViewModel(),
    updateParentName: (String) -> Unit,
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    viewModel.sideEffect.collectWithLifecycle { sideEffect ->
        when (sideEffect) {
            is NameSideEffect.UpdateParentName -> updateParentName(sideEffect.name)
        }
    }

    LaunchedEffect(key1 = Unit) {
        updateParentName(uiState.name)
    }

    NameContent(
        uiState = uiState,
        onTextChangeName = viewModel::updateName,
    )
}

@Composable
fun NameContent(
    uiState: NameState = NameState(),
    onTextChangeName: (String) -> Unit = {},
    friendList: List<String> = emptyList(),
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                horizontal = SusuTheme.spacing.spacing_m,
                vertical = SusuTheme.spacing.spacing_xl,
            ),
    ) {
        Text(
            text = stringResource(R.string.name_content_title),
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
            placeholder = stringResource(R.string.name_content_placeholder),
            placeholderColor = Gray40,
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.size(SusuTheme.spacing.spacing_xl))

        if (friendList.isNotEmpty()) {
            // TODO: 친구 목록 서버 연동
            LazyColumn {
                items(friendList) { friend ->
                    FriendListItem(friend)
                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF6F6F6)
@Composable
fun NameContentPreview() {
    SusuTheme {
        val friendList = listOf("김철수", "국영수", "가나다")

        NameContent(friendList = friendList)
    }
}
