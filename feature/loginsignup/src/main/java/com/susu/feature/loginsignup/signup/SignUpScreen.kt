package com.susu.feature.loginsignup.signup

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.susu.core.designsystem.R
import com.susu.core.designsystem.component.appbar.SusuDefaultAppBar
import com.susu.core.designsystem.component.appbar.SusuProgressAppBar
import com.susu.core.designsystem.component.button.FilledButtonColor
import com.susu.core.designsystem.component.button.MediumButtonStyle
import com.susu.core.designsystem.component.button.SusuFilledButton
import com.susu.core.designsystem.theme.SusuTheme
import com.susu.core.ui.extension.susuClickable

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
        onPreviousPressed = viewModel::goPreviousStep,
        onNextPressed = viewModel::goNextStep,
    ) {
        AnimatedContent(
            modifier = Modifier.weight(1f),
            targetState = uiState,
            label = "SignUpContent",
            transitionSpec = {
                val direction = if (targetState.currentStep.ordinal > initialState.currentStep.ordinal)
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
            when (targetState.currentStep) {
                SignUpStep.TERMS -> {
                    Text(text = targetState.currentStep.description)
                }

                SignUpStep.NAME -> {
                    Text(text = targetState.currentStep.description)
                }

                SignUpStep.ADDITIONAL -> {
                    Text(text = targetState.currentStep.description)
                }
            }
        }
    }
}

@Composable
fun SignUpScreen(
    uiState: SignUpState = SignUpState(),
    onPreviousPressed: () -> Unit = {},
    onNextPressed: () -> Unit = {},
    content: @Composable ColumnScope.() -> Unit = {},
) {
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        if (uiState.currentStep == SignUpStep.TERMS) {
            SusuDefaultAppBar(
                leftIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_arrow_left),
                        contentDescription = "뒤로가기",
                        modifier = Modifier.susuClickable(
                            rippleEnabled = true,
                            onClick = onPreviousPressed,
                        ).padding(10.dp),
                    )
                },
                title = uiState.currentStep.appBarTitle,
            )
        } else {
            SusuProgressAppBar(
                leftIcon = R.drawable.ic_arrow_left,
                currentStep = SignUpStep.entries.indexOf(uiState.currentStep),
                entireStep = SignUpStep.entries.size - 1,
                onClickBackButton = onPreviousPressed,
            )
        }
        content()
        SusuFilledButton(
            modifier = Modifier.fillMaxWidth(),
            color = FilledButtonColor.Black,
            style = MediumButtonStyle.height60,
            text = "다음",
            isActive = uiState.isNextStepAvailable,
            onClick = onNextPressed,
        )
    }
}

@Preview
@Composable
fun SignUpScreenPreview() {
    SusuTheme {
        SignUpScreen {
            Text("hello", modifier = Modifier.fillMaxWidth().weight(1f))
        }
    }
}
