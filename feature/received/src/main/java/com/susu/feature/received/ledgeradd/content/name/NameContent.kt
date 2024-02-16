package com.susu.feature.received.ledgeradd.content.name

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import com.susu.core.designsystem.theme.SusuTheme
import com.susu.core.ui.SnackbarToken
import com.susu.core.ui.extension.collectWithLifecycle
import com.susu.feature.received.R
import kotlinx.coroutines.android.awaitFrame
import kotlinx.coroutines.launch

@Composable
fun NameContentRoute(
    viewModel: NameViewModel = hiltViewModel(),
    updateParentName: (String) -> Unit = {},
    onShowSnackbar: (SnackbarToken) -> Unit,
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    val focusRequester = remember { FocusRequester() }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    viewModel.sideEffect.collectWithLifecycle { sideEffect ->
        when (sideEffect) {
            is NameSideEffect.UpdateParentName -> {
                updateParentName(sideEffect.name)
            }

            NameSideEffect.ShowKeyboard -> scope.launch {
                awaitFrame()
                focusRequester.requestFocus()
            }

            NameSideEffect.ShowNotValidSnackbar -> onShowSnackbar(
                SnackbarToken(
                    message = context.getString(R.string.ledger_snackbar_category_name_validation),
                )
            )
        }
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.showKeyboardIfTextEmpty()
    }

    NameContent(
        uiState = uiState,
        focusRequester = focusRequester,
        onTextChangeName = viewModel::updateName,
    )
}

@Composable
fun NameContent(
    uiState: NameState = NameState(),
    focusRequester: FocusRequester = remember { FocusRequester() },
    onTextChangeName: (String) -> Unit = {},
) {
    LaunchedEffect(key1 = Unit) {
        onTextChangeName(uiState.name)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = SusuTheme.spacing.spacing_xl,
                start = SusuTheme.spacing.spacing_m,
                end = SusuTheme.spacing.spacing_m,
            ),
    ) {
        Text(
            text = stringResource(R.string.input_name_screen_title),
            style = SusuTheme.typography.title_m,
        )

        Spacer(modifier = Modifier.size(SusuTheme.spacing.spacing_m))

        SusuBasicTextField(
            modifier = Modifier.focusRequester(focusRequester),
            text = uiState.name,
            onTextChange = onTextChangeName,
            placeholder = stringResource(R.string.input_name_screen_textfield_placeholder),
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF6F6F6)
@Composable
fun NameContentPreview() {
    SusuTheme {
        NameContent()
    }
}
