package com.susu.feature.community.voteadd

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.susu.core.designsystem.component.appbar.SusuDefaultAppBar
import com.susu.core.designsystem.component.appbar.icon.BackIcon
import com.susu.core.designsystem.component.appbar.icon.RegisterText
import com.susu.core.designsystem.component.button.FilledButtonColor
import com.susu.core.designsystem.component.button.SusuFilledButton
import com.susu.core.designsystem.component.button.XSmallButtonStyle
import com.susu.core.designsystem.component.screen.LoadingScreen
import com.susu.core.designsystem.component.textfield.SusuBasicTextField
import com.susu.core.designsystem.component.textfieldbutton.SusuTextFieldFillMaxButton
import com.susu.core.designsystem.component.textfieldbutton.TextFieldButtonColor
import com.susu.core.designsystem.component.textfieldbutton.style.MediumTextFieldButtonStyle
import com.susu.core.designsystem.theme.Gray10
import com.susu.core.designsystem.theme.Gray100
import com.susu.core.designsystem.theme.Gray40
import com.susu.core.designsystem.theme.Orange60
import com.susu.core.designsystem.theme.SusuTheme
import com.susu.core.model.Category
import com.susu.core.ui.extension.collectWithLifecycle
import com.susu.core.ui.extension.susuClickable
import com.susu.feature.community.R

@Composable
fun VoteAddRoute(
    viewModel: VoteAddViewModel = hiltViewModel(),
    popBackStack: () -> Unit,
    popBackStackWithVote: (String) -> Unit,
    handleException: (Throwable, () -> Unit) -> Unit,
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    viewModel.sideEffect.collectWithLifecycle { sideEffect ->
        when (sideEffect) {
            is VoteAddSideEffect.HandleException -> handleException(sideEffect.throwable, sideEffect.retry)
            VoteAddSideEffect.PopBackStack -> popBackStack()
            is VoteAddSideEffect.PopBackStackWithVote -> popBackStackWithVote(sideEffect.vote)
        }
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.getCategoryConfig()
    }

    VoteAddScreen(
        uiState = uiState,
        onClickBack = viewModel::popBackStack,
        onClickRegister = viewModel::createVote,
        onClickCategoryButton = viewModel::selectCategory,
        onTextChangeContent = viewModel::updateContent,
        onTextChangeOptionContent = viewModel::updateOptionContent,
        onClickOptionFilledButton = viewModel::toggleOptionSavedState,
        onClickOptionClearIcon = { index -> viewModel.updateOptionContent(index, "") },
        onClickOptionCloseIcon = viewModel::removeOptionState,
        onClickAddOptionButton = viewModel::addOptionState,
    )
}

@Composable
fun VoteAddScreen(
    uiState: VoteAddState = VoteAddState(),
    onClickBack: () -> Unit = {},
    onClickRegister: () -> Unit = {},
    onClickCategoryButton: (Category) -> Unit = {},
    onTextChangeContent: (String) -> Unit = {},
    onTextChangeOptionContent: (Int, String) -> Unit = { _, _ -> },
    onClickOptionFilledButton: (Int) -> Unit = {},
    onClickOptionClearIcon: (Int) -> Unit = {},
    onClickOptionCloseIcon: (Int) -> Unit = {},
    onClickAddOptionButton: () -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SusuTheme.colorScheme.background10),
    ) {
        SusuDefaultAppBar(
            leftIcon = {
                BackIcon(
                    onClick = onClickBack,
                )
            },
            title = stringResource(R.string.vote_add_screen_title),
            actions = {
                RegisterText(
                    modifier = Modifier
                        .padding(end = SusuTheme.spacing.spacing_m)
                        .susuClickable(
                            rippleEnabled = false,
                            runIf = uiState.buttonEnabled,
                            onClick = onClickRegister,
                        ),
                    color = if (uiState.buttonEnabled) Gray100 else Gray40,
                )
            },
        )

        Column(
            modifier = Modifier
                .padding(SusuTheme.spacing.spacing_m)
                .weight(1f, fill = false)
                .verticalScroll(rememberScrollState()),
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(SusuTheme.spacing.spacing_xxxxs),
            ) {
                uiState.categoryConfigList.forEach {
                    SusuFilledButton(
                        color = FilledButtonColor.Black,
                        style = XSmallButtonStyle.height28,
                        text = it.name,
                        isActive = it == uiState.selectedCategory,
                        onClick = { onClickCategoryButton(it) },
                    )
                }
            }

            Spacer(modifier = Modifier.size(SusuTheme.spacing.spacing_m))

            SusuBasicTextField(
                modifier = Modifier.fillMaxWidth(),
                text = uiState.content,
                onTextChange = onTextChangeContent,
                textStyle = SusuTheme.typography.text_xxs,
                maxLines = 10,
                placeholder = stringResource(R.string.vote_add_screen_textfield_placeholder),
            )

            Spacer(modifier = Modifier.size(SusuTheme.spacing.spacing_xl))

            Column(
                verticalArrangement = Arrangement.spacedBy(SusuTheme.spacing.spacing_xxs),
            ) {
                uiState.voteOptionStateList.forEachIndexed { index, option ->
                    SusuTextFieldFillMaxButton(
                        text = option.content,
                        onTextChange = { text -> onTextChangeOptionContent(index, text) },
                        onClickFilledButton = { onClickOptionFilledButton(index) },
                        onClickClearIcon = { onClickOptionClearIcon(index) },
                        onClickCloseIcon = { onClickOptionCloseIcon(index) },
                        isSaved = option.isSaved,
                        style = MediumTextFieldButtonStyle.height52,
                        color = TextFieldButtonColor.Gray,
                        placeholder = stringResource(R.string.vote_add_screen_textfield_button_placeholder),
                    )
                }
            }
        }

        Icon(
            modifier = Modifier
                .imePadding()
                .clip(CircleShape)
                .susuClickable(onClick = onClickAddOptionButton)
                .background(Orange60)
                .padding(SusuTheme.spacing.spacing_xxxxs)
                .align(Alignment.CenterHorizontally),
            painter = painterResource(id = com.susu.core.designsystem.R.drawable.ic_floating_button_add),
            contentDescription = stringResource(R.string.vote_add_screen_content_description_vote_add_button),
            tint = Gray10,
        )
    }

    if (uiState.isLoading) {
        LoadingScreen()
    }
}

@Preview
@Composable
fun VoteAddScreenPreview() {
    SusuTheme {
        VoteAddScreen()
    }
}
