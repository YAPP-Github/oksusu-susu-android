package com.susu.feature.received.ledgeradd

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.susu.core.designsystem.component.appbar.SusuProgressAppBar
import com.susu.core.designsystem.component.appbar.icon.BackIcon
import com.susu.core.designsystem.component.button.FilledButtonColor
import com.susu.core.designsystem.component.button.MediumButtonStyle
import com.susu.core.designsystem.component.button.SusuFilledButton
import com.susu.core.designsystem.component.screen.LoadingScreen
import com.susu.core.designsystem.theme.SusuTheme
import com.susu.core.model.Category
import com.susu.core.ui.R
import com.susu.core.ui.SnackbarToken
import com.susu.core.ui.extension.collectWithLifecycle
import com.susu.core.ui.extension.susuDefaultAnimatedContentTransitionSpec
import com.susu.feature.received.ledgeradd.content.category.CategoryContentRoute
import com.susu.feature.received.ledgeradd.content.date.DateContentRoute
import com.susu.feature.received.ledgeradd.content.name.NameContentRoute
import java.time.LocalDateTime

@Composable
fun LedgerAddRoute(
    viewModel: LedgerAddViewModel = hiltViewModel(),
    popBackStack: () -> Unit,
    popBackStackWithLedger: (String) -> Unit,
    onShowSnackbar: (SnackbarToken) -> Unit,
    handleException: (Throwable, () -> Unit) -> Unit,
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    val keyboardController = LocalSoftwareKeyboardController.current
    viewModel.sideEffect.collectWithLifecycle { sideEffect ->
        when (sideEffect) {
            LedgerAddSideEffect.PopBackStack -> popBackStack()
            is LedgerAddSideEffect.HandleException -> handleException(sideEffect.throwable, sideEffect.retry)
            is LedgerAddSideEffect.PopBackStackWithLedger -> popBackStackWithLedger(sideEffect.ledger)
            LedgerAddSideEffect.HideKeyboard -> keyboardController?.hide()
        }
    }

    var dateContentCategory: Category? by remember {
        mutableStateOf(null)
    }

    var dateContentName: String by remember {
        mutableStateOf("")
    }

    BackHandler {
        viewModel.goToPrevStep()
    }

    LedgerAddScreen(
        uiState = uiState,
        onClickBack = viewModel::goToPrevStep,
        onClickNextButton = viewModel::goToNextStep,
        updateParentSelectedCategory = { category ->
            viewModel.updateSelectedCategory(category)
            dateContentCategory = category
        },
        updateParentName = { name ->
            viewModel.updateName(name)
            dateContentName = name
        },
        dateContentName = dateContentName,
        dateContentCategory = dateContentCategory,
        updateParentDate = { startAt, endAt ->
            viewModel.updateDate(startAt, endAt)
        },
        onShowSnackbar = onShowSnackbar,
    )
}

@Composable
fun LedgerAddScreen(
    uiState: LedgerAddState = LedgerAddState(),
    onClickBack: () -> Unit = {},
    onClickNextButton: () -> Unit = {},
    updateParentSelectedCategory: (Category?) -> Unit = {},
    updateParentName: (String) -> Unit = {},
    dateContentCategory: Category? = Category(),
    dateContentName: String = "",
    updateParentDate: (LocalDateTime?, LocalDateTime?) -> Unit = { _, _ -> },
    onShowSnackbar: (SnackbarToken) -> Unit = {},
) {
    Column(
        modifier = Modifier
            .background(SusuTheme.colorScheme.background15)
            .fillMaxSize(),
    ) {
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
                LedgerAddStep.CATEGORY -> CategoryContentRoute(
                    updateParentSelectedCategory = updateParentSelectedCategory,
                    onShowSnackbar = onShowSnackbar,
                )

                LedgerAddStep.NAME -> NameContentRoute(
                    updateParentName = updateParentName,
                    onShowSnackbar = onShowSnackbar,
                )

                LedgerAddStep.DATE -> DateContentRoute(
                    name = dateContentName,
                    category = dateContentCategory,
                    updateParentDate = updateParentDate,
                )
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

    if (uiState.isLoading) {
        LoadingScreen()
    }
}

@Preview
@Composable
fun ReceivedScreenPreview() {
    SusuTheme {
        LedgerAddScreen()
    }
}
