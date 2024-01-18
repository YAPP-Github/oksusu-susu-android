package com.susu.feature.received.ledgeradd

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.susu.core.designsystem.component.appbar.SusuProgressAppBar
import com.susu.core.designsystem.component.appbar.icon.BackIcon
import com.susu.core.designsystem.component.button.FilledButtonColor
import com.susu.core.designsystem.component.button.MediumButtonStyle
import com.susu.core.designsystem.component.button.SusuFilledButton
import com.susu.core.designsystem.theme.SusuTheme
import com.susu.core.model.Category
import com.susu.core.ui.R
import com.susu.core.ui.extension.collectWithLifecycle
import com.susu.core.ui.extension.susuDefaultAnimatedContentTransitionSpec
import com.susu.feature.received.Category.category.CategoryViewModel
import com.susu.feature.received.ledgeradd.category.CategoryContent
import com.susu.feature.received.ledgeradd.category.CategorySideEffect
import com.susu.feature.received.ledgeradd.category.CategoryState
import com.susu.feature.received.ledgeradd.content.DateContent
import com.susu.feature.received.ledgeradd.content.NameContent
import kotlinx.coroutines.android.awaitFrame
import kotlinx.coroutines.launch


@Composable
fun LedgerAddRoute(
    viewModel: LedgerAddViewModel = hiltViewModel(),
    categoryViewModel: CategoryViewModel = hiltViewModel(),
    popBackStack: () -> Unit,
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    viewModel.sideEffect.collectWithLifecycle { sideEffect ->
        when (sideEffect) {
            LedgerAddSideEffect.PopBackStack -> popBackStack()
        }
    }

    val categoryState = categoryViewModel.uiState.collectAsStateWithLifecycle().value
    val focusRequester = remember { FocusRequester() }
    val scope = rememberCoroutineScope()
    categoryViewModel.sideEffect.collectWithLifecycle { sideEffect ->
        when (sideEffect) {
            is CategorySideEffect.UpdateParentSelectedCategory -> viewModel.updateSelectedCategory(sideEffect.category)
            CategorySideEffect.FocusCustomCategory -> scope.launch {
                awaitFrame()
                focusRequester.requestFocus()
            }
        }
    }

    LaunchedEffect(key1 = Unit) {
        categoryViewModel.getCategoryConfig()
    }

    BackHandler {
        viewModel.goToPrevStep()
    }

    LedgerAddScreen(
        uiState = uiState,
        onClickBack = viewModel::goToPrevStep,
        onClickNextButton = viewModel::goToNextStep,
        categoryState = categoryState,
        focusRequester = focusRequester,
        onClickCategoryButton = categoryViewModel::selectCategory,
        onClickCustomCategoryButton = categoryViewModel::showCustomCategoryTextField,
        onClickCustomCategoryTextFieldCloseIcon = categoryViewModel::hideCustomCategoryTextField,
        onClickCustomCategoryTextField = categoryViewModel::selectCustomCategory,
        onClickCustomCategoryTextFieldClearIcon = { categoryViewModel.updateCustomCategoryText("") },
        onTextChangeCustomCategoryTextField = categoryViewModel::updateCustomCategoryText,
        onClickTextFieldInnerButton = categoryViewModel::toggleTextFieldSaved,
        updateParentSelectedCategory = categoryViewModel::updateParentSelectedCategory,
    )
}

@Composable
fun LedgerAddScreen(
    uiState: LedgerAddState = LedgerAddState(),
    onClickBack: () -> Unit = {},
    onClickNextButton: () -> Unit = {},
    categoryState: CategoryState = CategoryState(),
    focusRequester: FocusRequester = remember { FocusRequester() },
    onClickCategoryButton: (Category) -> Unit = {},
    onClickCustomCategoryButton: () -> Unit = {},
    onClickCustomCategoryTextFieldCloseIcon: () -> Unit = {},
    onClickCustomCategoryTextField: () -> Unit = {},
    onClickCustomCategoryTextFieldClearIcon: () -> Unit = {},
    onTextChangeCustomCategoryTextField: (String) -> Unit = {},
    onClickTextFieldInnerButton: () -> Unit = {},
    updateParentSelectedCategory: () -> Unit = {},
) {
    Box(
        modifier = Modifier
            .background(SusuTheme.colorScheme.background15)
            .fillMaxSize(),
    ) {
        Column {
            SusuProgressAppBar(
                leftIcon = {
                    BackIcon(onClickBack)
                },
                entireStep = LedgerAddStep.entries.size,
                currentStep = uiState.currentStep.ordinal + 1,
            )

            AnimatedContent(
                modifier = Modifier.weight(1f),
                targetState = uiState.currentStep,
                label = "LedgerAddScreen",
                transitionSpec = {
                    susuDefaultAnimatedContentTransitionSpec(
                        leftDirectionCondition = targetState.ordinal > initialState.ordinal,
                    )
                },
            ) { targetState ->
                when (targetState) {
                    LedgerAddStep.CATEGORY -> CategoryContent(
                        uiState = categoryState,
                        focusRequester = focusRequester,
                        onClickCategoryButton = onClickCategoryButton,
                        onClickCustomCategoryButton = onClickCustomCategoryButton,
                        onClickCustomCategoryTextFieldCloseIcon = onClickCustomCategoryTextFieldCloseIcon,
                        onClickCustomCategoryTextField = onClickCustomCategoryTextField,
                        onClickCustomCategoryTextFieldClearIcon = onClickCustomCategoryTextFieldClearIcon,
                        onTextChangeCustomCategoryTextField = onTextChangeCustomCategoryTextField,
                        onClickTextFieldInnerButton = onClickTextFieldInnerButton,
                        updateParentSelectedCategory = updateParentSelectedCategory,
                    )

                    LedgerAddStep.NAME -> NameContent()
                    LedgerAddStep.DATE -> DateContent()
                }
            }

            SusuFilledButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .imePadding(),
                shape = RectangleShape,
                color = FilledButtonColor.Black,
                style = MediumButtonStyle.height60,
                text = stringResource(id = R.string.word_save),
                isClickable = uiState.buttonEnabled,
                isActive = uiState.buttonEnabled,
                onClick = onClickNextButton,
            )
        }
    }
}

@Preview
@Composable
fun ReceivedScreenPreview() {
    SusuTheme {
        LedgerAddScreen()
    }
}
