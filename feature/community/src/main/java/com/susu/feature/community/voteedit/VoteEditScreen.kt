package com.susu.feature.community.voteedit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.susu.core.model.Vote
import com.susu.core.ui.extension.collectWithLifecycle
import com.susu.core.ui.extension.susuClickable
import com.susu.feature.community.R
import com.susu.feature.community.community.component.VoteCard
import com.susu.feature.community.votedetail.component.VoteItem

@Composable
fun VoteEditRoute(
    viewModel: VoteEditViewModel = hiltViewModel(),
    popBackStack: () -> Unit,
    handleException: (Throwable, () -> Unit) -> Unit,
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    viewModel.sideEffect.collectWithLifecycle { sideEffect ->
        when (sideEffect) {
            is VoteEditSideEffect.HandleException -> handleException(sideEffect.throwable, sideEffect.retry)
            VoteEditSideEffect.PopBackStack -> popBackStack()
        }
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.initData()
        viewModel.getCategoryConfig()
    }

    VoteEditScreen(
        uiState = uiState,
        onClickBack = viewModel::popBackStack,
        onClickRegister = viewModel::editVote,
        onClickCategoryButton = viewModel::selectCategory,
    )
}

@Composable
fun VoteEditScreen(
    uiState: VoteEditState = VoteEditState(),
    onClickBack: () -> Unit = {},
    onClickRegister: () -> Unit = {},
    onClickCategoryButton: (Category) -> Unit = {},
    onTextChangeContent: (String) -> Unit = {},
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
            title = "투표 편집",
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
                uiState.voteOptionStateList.forEach { option ->
                    VoteItem(title = option, showResult = false)
                }
            }
        }
    }

    if (uiState.isLoading) {
        LoadingScreen()
    }
}

@Preview
@Composable
fun VoteEditScreenPreview() {
    SusuTheme {
        VoteEditScreen()
    }
}