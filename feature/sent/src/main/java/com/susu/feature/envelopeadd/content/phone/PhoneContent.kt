package com.susu.feature.envelopeadd.content.phone

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.susu.core.designsystem.component.textfield.SusuBasicTextField
import com.susu.core.designsystem.theme.Gray40
import com.susu.core.designsystem.theme.Gray60
import com.susu.core.designsystem.theme.SusuTheme
import com.susu.core.ui.SnackbarToken
import com.susu.core.ui.extension.collectWithLifecycle
import com.susu.core.ui.util.AnnotatedText
import com.susu.core.ui.util.PhoneVisualTransformation
import com.susu.feature.sent.R
import kotlinx.coroutines.android.awaitFrame
import kotlinx.coroutines.launch

@Composable
fun PhoneContentRoute(
    viewModel: PhoneViewModel = hiltViewModel(),
    friendName: String,
    updateParentPhone: (String?) -> Unit,
    onShowSnackbar: (SnackbarToken) -> Unit,
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    val context = LocalContext.current
    val focusRequester = remember { FocusRequester() }
    val scope = rememberCoroutineScope()

    viewModel.sideEffect.collectWithLifecycle { sideEffect ->
        when (sideEffect) {
            is PhoneSideEffect.UpdateParentPhone -> updateParentPhone(sideEffect.phone)
            PhoneSideEffect.ShowNotValidSnackbar -> onShowSnackbar(
                SnackbarToken(
                    message = context.getString(R.string.sent_snackbar_phone_validation),
                    extraPadding = PaddingValues(bottom = 60.dp),
                ),
            )

            PhoneSideEffect.ShowKeyboard -> scope.launch {
                awaitFrame()
                focusRequester.requestFocus()
            }
        }
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.showKeyboardIfTextEmpty()
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.updateName(friendName)
        viewModel.updatePhone(uiState.phone)
    }

    PhoneContent(
        uiState = uiState,
        focusRequester = focusRequester,
        onTextChangePhone = viewModel::updatePhone,
    )
}

@Composable
fun PhoneContent(
    modifier: Modifier = Modifier,
    focusRequester: FocusRequester = remember { FocusRequester() },
    padding: PaddingValues = PaddingValues(
        horizontal = SusuTheme.spacing.spacing_m,
        vertical = SusuTheme.spacing.spacing_xl,
    ),
    uiState: PhoneState = PhoneState(),
    onTextChangePhone: (String) -> Unit = {},
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(padding),
    ) {
        AnnotatedText(
            originalText = stringResource(R.string.sent_envelope_add_phone_title, uiState.name),
            originalTextStyle = SusuTheme.typography.title_m,
            targetTextList = listOf(stringResource(R.string.sent_envelope_add_phone_title_highlight, uiState.name)),
            spanStyle = SusuTheme.typography.title_m.copy(Gray60).toSpanStyle(),
        )
        Spacer(
            modifier = modifier
                .size(SusuTheme.spacing.spacing_m),
        )
        SusuBasicTextField(
            text = uiState.phone,
            onTextChange = onTextChangePhone,
            placeholder = stringResource(id = R.string.sent_envelope_add_phone_placeholder),
            placeholderColor = Gray40,
            modifier = modifier.fillMaxWidth().focusRequester(focusRequester),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            visualTransformation = PhoneVisualTransformation(),
        )
        Spacer(modifier = modifier.size(SusuTheme.spacing.spacing_xl))
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF6F6F6)
@Composable
fun PhoneContentPreview() {
    SusuTheme {
        PhoneContent()
    }
}
