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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
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
import com.susu.core.ui.R
import com.susu.core.ui.extension.collectWithLifecycle
import com.susu.core.ui.extension.susuDefaultAnimatedContentTransitionSpec
import com.susu.feature.received.ledgeradd.content.CategoryContent
import com.susu.feature.received.ledgeradd.content.DateContent
import com.susu.feature.received.ledgeradd.content.NameContent



@Composable
fun LedgerAddRoute(
    viewModel: LedgerAddViewModel = hiltViewModel(),
    popBackStack: () -> Unit,
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    viewModel.sideEffect.collectWithLifecycle { sideEffect ->
        when (sideEffect) {
            LedgerAddSideEffect.PopBackStack -> popBackStack()
        }
    }

    BackHandler {
        viewModel.goToPrevStep()
    }

    LedgerAddScreen(
        uiState = uiState,
        onClickBack = viewModel::goToPrevStep,
        onClickNextButton = viewModel::goToNextStep,
    )
}

@Composable
fun LedgerAddScreen(
    uiState: LedgerAddState = LedgerAddState(),
    onClickBack: () -> Unit = {},
    onClickNextButton: () -> Unit = {},
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
                    LedgerAddStep.CATEGORY -> CategoryContent()
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
