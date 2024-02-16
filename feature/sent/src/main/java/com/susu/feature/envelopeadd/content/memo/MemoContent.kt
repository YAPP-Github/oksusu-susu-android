package com.susu.feature.envelopeadd.content.memo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.susu.core.designsystem.component.textfield.SusuBasicTextField
import com.susu.core.designsystem.theme.Gray100
import com.susu.core.designsystem.theme.Gray40
import com.susu.core.designsystem.theme.SusuTheme
import com.susu.core.ui.SnackbarToken
import com.susu.core.ui.extension.collectWithLifecycle
import com.susu.feature.sent.R
import kotlinx.coroutines.android.awaitFrame
import kotlinx.coroutines.launch

@Composable
fun MemoContentRoute(
    viewModel: MemoViewModel = hiltViewModel(),
    updateParentMemo: (String?) -> Unit,
    onShowSnackbar: (SnackbarToken) -> Unit,
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    val context = LocalContext.current

    val focusRequester = remember { FocusRequester() }
    val scope = rememberCoroutineScope()

    viewModel.sideEffect.collectWithLifecycle { sideEffect ->
        when (sideEffect) {
            is MemoSideEffect.UpdateParentMemo -> updateParentMemo(sideEffect.memo)
            MemoSideEffect.ShowNotValidSnackbar -> onShowSnackbar(
                SnackbarToken(
                    message = context.getString(R.string.sent_snackbar_memo_validation),
                ),
            )

            MemoSideEffect.ShowKeyboard -> scope.launch {
                awaitFrame()
                focusRequester.requestFocus()
            }
        }
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.showKeyboardIfTextEmpty()
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.updateMemo(uiState.memo)
    }

    MemoContent(
        uiState = uiState,
        focusRequester = focusRequester,
        onTextChangeMemo = viewModel::updateMemo,
    )
}

@Composable
fun MemoContent(
    modifier: Modifier = Modifier,
    focusRequester: FocusRequester = remember { FocusRequester() },
    padding: PaddingValues = PaddingValues(
        horizontal = SusuTheme.spacing.spacing_m,
        vertical = SusuTheme.spacing.spacing_xl,
    ),
    uiState: MemoState = MemoState(),
    onTextChangeMemo: (String) -> Unit = {},
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(padding),
    ) {
        Text(
            text = stringResource(id = R.string.sent_envelope_add_memo_title),
            style = SusuTheme.typography.title_m,
            color = Gray100,
        )
        Spacer(
            modifier = modifier
                .size(SusuTheme.spacing.spacing_m),
        )
        SusuBasicTextField(
            text = uiState.memo,
            onTextChange = onTextChangeMemo,
            placeholder = stringResource(id = R.string.sent_envelope_add_memo_placeholder),
            placeholderColor = Gray40,
            modifier = modifier.fillMaxWidth().focusRequester(focusRequester),
            maxLines = 5,
        )
        Spacer(modifier = modifier.size(SusuTheme.spacing.spacing_xl))
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF6F6F6)
@Composable
fun MemoContentPreview() {
    SusuTheme {
        MemoContent()
    }
}
