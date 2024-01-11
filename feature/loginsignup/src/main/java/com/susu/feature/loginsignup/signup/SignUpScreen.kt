package com.susu.feature.loginsignup.signup

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.susu.core.designsystem.component.appbar.SusuDefaultAppBar
import com.susu.core.designsystem.component.appbar.SusuProgressAppBar
import com.susu.core.designsystem.component.appbar.icon.BackIcon
import com.susu.core.designsystem.component.button.FilledButtonColor
import com.susu.core.designsystem.component.button.MediumButtonStyle
import com.susu.core.designsystem.component.button.SusuFilledButton
import com.susu.core.designsystem.theme.SusuTheme
import com.susu.core.model.Term
import com.susu.feature.loginsignup.signup.content.TermsContent

@Composable
fun SignUpRoute(
    viewModel: SignUpViewModel = hiltViewModel(),
    navigateToReceived: () -> Unit,
    navigateToLogin: () -> Unit,
) {
    val uiState: SignUpState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = viewModel.sideEffect) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                SignUpEffect.NavigateToLogin -> navigateToLogin()
                SignUpEffect.NavigateToReceived -> navigateToReceived()
                is SignUpEffect.ShowToast -> {}
            }
        }
    }

    SignUpScreen(
        uiState = uiState,
        isNextStepActive = when (uiState.currentStep) {
            SignUpStep.TERMS -> uiState.agreedTerms.containsAll(listOf(1, 2))
            SignUpStep.TERM_DETAIL -> true
            SignUpStep.NAME -> true
            SignUpStep.ADDITIONAL -> true
        },
        onPreviousPressed = viewModel::goPreviousStep,
        onNextPressed = {
            if (uiState.currentStep == SignUpStep.TERM_DETAIL) {
                // TODO: 현재 보고 있던 약관 동의
            }
            viewModel.goNextStep()
        },
    ) {
        AnimatedContent(
            modifier = Modifier.weight(1f),
            targetState = uiState.currentStep,
            label = "SignUpContent",
            transitionSpec = {
                val direction = if (targetState.ordinal > initialState.ordinal)
                    AnimatedContentTransitionScope.SlideDirection.Left
                else AnimatedContentTransitionScope.SlideDirection.Right
                slideIntoContainer(
                    towards = direction,
                    animationSpec = tween(500),
                ) togetherWith slideOutOfContainer(
                    towards = direction,
                    animationSpec = tween(500),
                )
            },
        ) { targetState ->
            when (targetState) {
                SignUpStep.TERMS -> {
                    TermsContent(
                        modifier = Modifier.fillMaxSize(),
                        descriptionText = targetState.description,
                        terms = listOf(
                            Term(id = 1, title = "아무개 약관", isEssential = true),
                            Term(id = 2, title = "아무개 약관", isEssential = true),
                        ),
                        agreedTerms = uiState.agreedTerms,
                        onDetailClick = viewModel::goTermDetail,
                        onSelectAll = { if (it) viewModel.agreeAllTerms() else viewModel.disagreeAllTerms() },
                        onTermChecked = { agree, id ->
                            if (agree) viewModel.agreeTerm(id) else viewModel.disagreeTerm(id)
                        },
                    )
                }

                SignUpStep.NAME -> {
                    Text(text = targetState.description)
                }

                SignUpStep.ADDITIONAL -> {
                    Text(text = targetState.description)
                }

                SignUpStep.TERM_DETAIL -> {
                    Text(text = targetState.description)
                }
            }
        }
    }
}

@Composable
fun SignUpScreen(
    uiState: SignUpState = SignUpState(),
    isNextStepActive: Boolean = false,
    onPreviousPressed: () -> Unit = {},
    onNextPressed: () -> Unit = {},
    content: @Composable ColumnScope.() -> Unit = {},
) {
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        if (uiState.currentStep == SignUpStep.TERMS || uiState.currentStep == SignUpStep.TERM_DETAIL) {
            SusuDefaultAppBar(
                leftIcon = {
                    BackIcon(
                        onClick = onPreviousPressed,
                    )
                },
                title = uiState.currentStep.appBarTitle,
            )
        } else {
            SusuProgressAppBar(
                leftIcon = {
                    BackIcon(
                        onClick = onPreviousPressed,
                    )
                },
                currentStep = SignUpStep.entries.indexOf(uiState.currentStep),
                entireStep = SignUpStep.entries.size - 1,
            )
        }
        content()
        SusuFilledButton(
            modifier = Modifier.fillMaxWidth(),
            shape = RectangleShape,
            color = FilledButtonColor.Black,
            style = MediumButtonStyle.height60,
            text = uiState.currentStep.bottomButtonText,
            isActive = isNextStepActive,
            onClick = onNextPressed,
        )
    }
}

@Preview
@Composable
fun SignUpScreenPreview() {
    SusuTheme {
        SignUpScreen {
            Text(
                "hello",
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
            )
        }
    }
}
