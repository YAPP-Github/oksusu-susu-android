package com.susu.feature.received.ledgeradd.category

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.susu.core.designsystem.component.button.FilledButtonColor
import com.susu.core.designsystem.component.button.GhostButtonColor
import com.susu.core.designsystem.component.button.MediumButtonStyle
import com.susu.core.designsystem.component.button.SusuFilledButton
import com.susu.core.designsystem.component.button.SusuGhostButton
import com.susu.core.designsystem.component.textfieldbutton.SusuTextFieldFillMaxButton
import com.susu.core.designsystem.component.textfieldbutton.style.MediumTextFieldButtonStyle
import com.susu.core.designsystem.theme.SusuTheme
import com.susu.core.model.Category
import com.susu.feature.received.R

@Composable
fun CategoryContent(
    uiState: CategoryState = CategoryState(),
    focusRequester: FocusRequester = remember { FocusRequester() },
    onClickCategoryButton: (Category) -> Unit = {},
    onClickCustomCategoryButton: () -> Unit = {},
    onClickCustomCategoryTextFieldCloseIcon: () -> Unit = {},
    onClickCustomCategoryTextField: () -> Unit = {},
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
                        onClick = { onClickCategoryButton(category) }
                    )
                }
            }

            if (uiState.showTextFieldButton) {
                SusuTextFieldFillMaxButton(
                    focusRequester = focusRequester,
                    style = MediumTextFieldButtonStyle.height60,
                    isSaved = uiState.isSavedCustomCategory,
                    isFocused = uiState.customCategory == uiState.selectedCategory,
                    placeholder = stringResource(com.susu.core.ui.R.string.word_input_placeholder),
                    onClickCloseIcon = onClickCustomCategoryTextFieldCloseIcon,
                    onClickButton = { onClickCustomCategoryTextField() },
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
