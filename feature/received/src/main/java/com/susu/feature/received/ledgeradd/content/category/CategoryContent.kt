package com.susu.feature.received.ledgeradd.content.category

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.susu.core.designsystem.component.button.FilledButtonColor
import com.susu.core.designsystem.component.button.GhostButtonColor
import com.susu.core.designsystem.component.button.MediumButtonStyle
import com.susu.core.designsystem.component.button.SusuFilledButton
import com.susu.core.designsystem.component.button.SusuGhostButton
import com.susu.core.designsystem.component.textfieldbutton.SusuTextFieldFillMaxButton
import com.susu.core.designsystem.component.textfieldbutton.TextFieldButtonColor
import com.susu.core.designsystem.component.textfieldbutton.style.MediumTextFieldButtonStyle
import com.susu.core.designsystem.theme.SusuTheme
import com.susu.core.model.Category
import com.susu.core.ui.SnackbarToken
import com.susu.core.ui.extension.collectWithLifecycle
import com.susu.feature.received.R
import kotlinx.coroutines.android.awaitFrame
import kotlinx.coroutines.launch

@Composable
fun CategoryContentRoute(
    viewModel: CategoryViewModel = hiltViewModel(),
    updateParentSelectedCategory: (Category?) -> Unit = {},
    onShowSnackbar: (SnackbarToken) -> Unit,
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    val focusRequester = remember { FocusRequester() }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    viewModel.sideEffect.collectWithLifecycle { sideEffect ->
        when (sideEffect) {
            is CategorySideEffect.UpdateParentSelectedCategory -> {
                updateParentSelectedCategory(sideEffect.category)
            }

            CategorySideEffect.FocusCustomCategory -> scope.launch {
                awaitFrame()
                focusRequester.requestFocus()
            }

            CategorySideEffect.ShowNotValidSnackbar -> onShowSnackbar(
                SnackbarToken(
                    message = context.getString(R.string.ledger_snackbar_category_validation),
                    extraPadding = PaddingValues(bottom = 60.dp),
                ),
            )
        }
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.getCategoryConfig()
    }

    LaunchedEffect(
        key1 = uiState.selectedCategory,
        key2 = uiState.isSavedCustomCategory,
    ) {
        snapshotFlow { uiState.selectedCategory }
            .collect {
                viewModel.updateParentSelectedCategory()
            }
    }

    CategoryContent(
        uiState = uiState,
        focusRequester = focusRequester,
        onClickCategoryButton = viewModel::selectCategory,
        onClickCustomCategoryButton = viewModel::showCustomCategoryTextField,
        onClickCustomCategoryTextFieldCloseIcon = viewModel::hideCustomCategoryTextField,
        onClickCustomCategoryTextField = viewModel::selectCustomCategory,
        onClickCustomCategoryTextFieldClearIcon = { viewModel.updateCustomCategoryText("") },
        onTextChangeCustomCategoryTextField = viewModel::updateCustomCategoryText,
        onClickTextFieldInnerButton = viewModel::toggleTextFieldSaved,
    )
}

@Composable
fun CategoryContent(
    uiState: CategoryState = CategoryState(),
    focusRequester: FocusRequester = remember { FocusRequester() },
    onClickCategoryButton: (Category) -> Unit = {},
    onClickCustomCategoryButton: () -> Unit = {},
    onClickCustomCategoryTextFieldCloseIcon: () -> Unit = {},
    onClickCustomCategoryTextField: () -> Unit = {},
    onClickCustomCategoryTextFieldClearIcon: () -> Unit = {},
    onTextChangeCustomCategoryTextField: (String) -> Unit = {},
    onClickTextFieldInnerButton: () -> Unit = {},
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = SusuTheme.spacing.spacing_xl,
                start = SusuTheme.spacing.spacing_m,
                end = SusuTheme.spacing.spacing_m,
            )
            .verticalScroll(scrollState),
    ) {
        Text(
            text = stringResource(R.string.select_category_screen_title),
            style = SusuTheme.typography.title_m,
        )

        Spacer(modifier = Modifier.size(SusuTheme.spacing.spacing_xxl))

        Column(
            verticalArrangement = Arrangement.spacedBy(SusuTheme.spacing.spacing_xxs),
        ) {
            uiState.categoryConfig.forEach { category ->
                if (category == uiState.selectedCategory) {
                    SusuFilledButton(
                        modifier = Modifier.fillMaxWidth(),
                        color = FilledButtonColor.Orange,
                        style = MediumButtonStyle.height60,
                        text = category.name,
                    )
                } else {
                    SusuGhostButton(
                        modifier = Modifier.fillMaxWidth(),
                        color = GhostButtonColor.Black,
                        style = MediumButtonStyle.height60,
                        text = category.name,
                        rippleEnabled = false,
                        onClick = { onClickCategoryButton(category) },
                    )
                }
            }

            if (uiState.showTextFieldButton) {
                SusuTextFieldFillMaxButton(
                    color = if (uiState.isCustomCategorySelected) TextFieldButtonColor.Orange else TextFieldButtonColor.Black,
                    text = uiState.customCategory.customCategory ?: "",
                    onTextChange = onTextChangeCustomCategoryTextField,
                    focusRequester = focusRequester,
                    style = MediumTextFieldButtonStyle.height60,
                    isSaved = uiState.isSavedCustomCategory,
                    isFocused = uiState.customCategory == uiState.selectedCategory,
                    placeholder = stringResource(com.susu.core.ui.R.string.word_input_placeholder),
                    onClickCloseIcon = onClickCustomCategoryTextFieldCloseIcon,
                    onClickClearIcon = onClickCustomCategoryTextFieldClearIcon,
                    onClickButton = { onClickCustomCategoryTextField() },
                    onClickFilledButton = onClickTextFieldInnerButton,
                )
            } else {
                SusuGhostButton(
                    modifier = Modifier.fillMaxWidth(),
                    color = GhostButtonColor.Black,
                    style = MediumButtonStyle.height60,
                    text = stringResource(com.susu.core.ui.R.string.word_input_yourself),
                    rippleEnabled = false,
                    onClick = onClickCustomCategoryButton,
                )
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF6F6F6)
@Composable
fun CategoryContentPreview() {
    SusuTheme {
        CategoryContent()
    }
}
