package com.susu.feature.received.envelopeadd.content.present

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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.susu.core.designsystem.component.textfield.SusuBasicTextField
import com.susu.core.designsystem.theme.Gray100
import com.susu.core.designsystem.theme.Gray40
import com.susu.core.designsystem.theme.SusuTheme
import com.susu.core.ui.SnackbarToken
import com.susu.core.ui.extension.collectWithLifecycle
import com.susu.feature.received.R
import kotlinx.coroutines.android.awaitFrame
import kotlinx.coroutines.launch

@Composable
fun PresentContentRoute(
    viewModel: PresentViewModel = hiltViewModel(),
    updateParentPresent: (String?) -> Unit,
    onShowSnackbar: (SnackbarToken) -> Unit,
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    val focusRequester = remember { FocusRequester() }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    viewModel.sideEffect.collectWithLifecycle { sideEffect ->
        when (sideEffect) {
            is PresentSideEffect.UpdateParentPresent -> updateParentPresent(sideEffect.present)
            PresentSideEffect.ShowKeyboard -> scope.launch {
                awaitFrame()
                focusRequester.requestFocus()
            }

            PresentSideEffect.ShowNotValidSnackbar -> onShowSnackbar(
                SnackbarToken(
                    message = context.getString(R.string.present_content_snackbar_validation),
                    extraPadding = PaddingValues(bottom = 60.dp),
                ),
            )
        }
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.showKeyboardIfTextEmpty()
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.updatePresent(uiState.present)
    }

    PresentContent(
        uiState = uiState,
        focusRequester = focusRequester,
        onTextChangeName = viewModel::updatePresent,
    )
}

@Composable
fun PresentContent(
    uiState: PresentState = PresentState(),
    focusRequester: FocusRequester = remember { FocusRequester() },
    onTextChangeName: (String) -> Unit = {},
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
            text = stringResource(R.string.present_content_title),
            style = SusuTheme.typography.title_m,
            color = Gray100,
        )
        Spacer(
            modifier = Modifier
                .size(SusuTheme.spacing.spacing_m),
        )
        SusuBasicTextField(
            text = uiState.present,
            onTextChange = onTextChangeName,
            placeholder = stringResource(R.string.present_content_placeholder),
            placeholderColor = Gray40,
            modifier = Modifier.fillMaxWidth().focusRequester(focusRequester),
            maxLines = 5,
        )
        Spacer(modifier = Modifier.size(SusuTheme.spacing.spacing_xl))
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF6F6F6)
@Composable
fun PresentContentPreview() {
    SusuTheme {
        PresentContent()
    }
}
