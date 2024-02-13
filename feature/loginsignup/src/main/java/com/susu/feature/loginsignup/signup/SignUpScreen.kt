package com.susu.feature.loginsignup.signup

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.susu.core.designsystem.component.appbar.SusuDefaultAppBar
import com.susu.core.designsystem.component.appbar.SusuProgressAppBar
import com.susu.core.designsystem.component.appbar.icon.BackIcon
import com.susu.core.designsystem.component.bottomsheet.datepicker.SusuYearPickerBottomSheet
import com.susu.core.designsystem.component.button.FilledButtonColor
import com.susu.core.designsystem.component.button.MediumButtonStyle
import com.susu.core.designsystem.component.button.SusuFilledButton
import com.susu.core.designsystem.component.screen.LoadingScreen
import com.susu.core.designsystem.theme.SusuTheme
import com.susu.core.ui.SnackbarToken
import com.susu.core.ui.USER_BIRTH_RANGE
import com.susu.core.ui.extension.collectWithLifecycle
import com.susu.feature.loginsignup.R
import com.susu.feature.loginsignup.signup.content.AdditionalContent
import com.susu.feature.loginsignup.signup.content.NameContent
import com.susu.feature.loginsignup.signup.content.TermsContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpRoute(
    padding: PaddingValues,
    viewModel: SignUpViewModel = hiltViewModel(),
    termViewModel: TermViewModel = hiltViewModel(),
    navigateToReceived: () -> Unit,
    navigateToLogin: () -> Unit,
    onShowSnackbar: (SnackbarToken) -> Unit = {},
) {
    val context = LocalContext.current
    val uiState: SignUpState by viewModel.uiState.collectAsStateWithLifecycle()
    val termState: TermState by termViewModel.uiState.collectAsStateWithLifecycle()

    BackHandler {
        viewModel.goPreviousStep()
    }

    viewModel.sideEffect.collectWithLifecycle { sideEffect ->
        when (sideEffect) {
            SignUpEffect.NavigateToLogin -> navigateToLogin()
            SignUpEffect.NavigateToReceived -> navigateToReceived()
            is SignUpEffect.ShowSnackbar -> onShowSnackbar(SnackbarToken(message = sideEffect.msg))
            SignUpEffect.ShowKakaoErrorSnackbar -> onShowSnackbar(
                SnackbarToken(
                    message = context.getString(R.string.signup_snackbar_kakao_login_error),
                ),
            )
        }
    }

    Box(modifier = Modifier.fillMaxSize().padding(padding)) {
        SignUpScreen(
            uiState = uiState,
            isNextStepActive = when (uiState.currentStep) {
                SignUpStep.TERMS -> uiState.agreedTerms.containsAll(termState.terms.filter { it.isEssential }.map { it.id })
                SignUpStep.TERM_DETAIL -> true
                SignUpStep.NAME -> uiState.isNameValid && uiState.name.isNotEmpty()
                SignUpStep.ADDITIONAL -> true
            },
            onPreviousPressed = viewModel::goPreviousStep,
            onNextPressed = {
                if (uiState.currentStep == SignUpStep.TERM_DETAIL) {
                    viewModel.agreeTerm(termState.currentTerm.id)
                }
                viewModel.goNextStep()
            },
        ) {
            AnimatedContent(
                modifier = Modifier.weight(1f),
                targetState = uiState.currentStep,
                label = "SignUpContent",
                transitionSpec = {
                    val direction = if (targetState.ordinal > initialState.ordinal) {
                        AnimatedContentTransitionScope.SlideDirection.Left
                    } else {
                        AnimatedContentTransitionScope.SlideDirection.Right
                    }
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
                            descriptionText = targetState.description?.let { stringResource(id = it) } ?: "",
                            terms = termState.terms,
                            agreedTerms = uiState.agreedTerms,
                            onDetailClick = {
                                termViewModel.updateCurrentTerm(it)
                                viewModel.goTermDetail()
                            },
                            onSelectAll = { agree ->
                                if (agree) {
                                    viewModel.agreeAllTerms(termState.terms.map { it.id })
                                } else {
                                    viewModel.disagreeAllTerms()
                                }
                            },
                            onTermChecked = { agree, id ->
                                if (agree) viewModel.agreeTerm(id) else viewModel.disagreeTerm(id)
                            },
                        )
                    }

                    SignUpStep.NAME -> {
                        NameContent(
                            modifier = Modifier.fillMaxSize(),
                            description = targetState.description?.let { stringResource(id = it) } ?: "",
                            text = uiState.name,
                            isError = uiState.isNameValid.not(),
                            onTextChange = viewModel::updateName,
                            onClickClearIcon = { viewModel.updateName("") },
                        )
                    }

                    SignUpStep.ADDITIONAL -> {
                        AdditionalContent(
                            modifier = Modifier.fillMaxSize(),
                            description = targetState.description?.let { stringResource(id = it) } ?: "",
                            selectedGender = uiState.gender,
                            selectedYear = uiState.birth,
                            onGenderSelect = viewModel::updateGender,
                            onYearClick = viewModel::showDatePicker,
                        )
                    }

                    SignUpStep.TERM_DETAIL -> {
                        Text(
                            modifier = Modifier
                                .fillMaxSize()
                                .verticalScroll(rememberScrollState())
                                .padding(SusuTheme.spacing.spacing_m),
                            text = termState.currentTerm.description,
                            style = SusuTheme.typography.text_xxxs,
                        )
                    }
                }
            }
        }

        if (uiState.showDatePicker) {
            SusuYearPickerBottomSheet(
                yearRange = USER_BIRTH_RANGE,
                reverseItemOrder = true,
                maximumContainerHeight = 322.dp,
                onDismissRequest = {
                    viewModel.updateBirth(it)
                    viewModel.hideDatePicker()
                },
            )
        }

        if (uiState.isLoading || termState.isLoading) {
            LoadingScreen(modifier = Modifier.align(Alignment.Center))
        }
    }
}

@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    uiState: SignUpState = SignUpState(),
    isNextStepActive: Boolean = false,
    onPreviousPressed: () -> Unit = {},
    onNextPressed: () -> Unit = {},
    content: @Composable ColumnScope.() -> Unit = {},
) {
    Column(
        modifier = modifier.fillMaxSize(),
    ) {
        if (uiState.currentStep == SignUpStep.TERMS || uiState.currentStep == SignUpStep.TERM_DETAIL) {
            SusuDefaultAppBar(
                leftIcon = {
                    BackIcon(
                        onClick = onPreviousPressed,
                    )
                },
                title = uiState.currentStep.appBarTitle?.let { stringResource(id = it) } ?: "",
            )
        } else {
            SusuProgressAppBar(
                leftIcon = {
                    BackIcon(
                        onClick = onPreviousPressed,
                    )
                },
                currentStep = SignUpStep.entries.indexOf(uiState.currentStep) - 1,
                entireStep = SignUpStep.entries.size - 2,
            )
        }
        content()
        SusuFilledButton(
            modifier = Modifier.fillMaxWidth().imePadding(),
            shape = RectangleShape,
            color = FilledButtonColor.Black,
            style = MediumButtonStyle.height60,
            text = stringResource(id = uiState.currentStep.bottomButtonText),
            isActive = isNextStepActive,
            isClickable = isNextStepActive,
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
