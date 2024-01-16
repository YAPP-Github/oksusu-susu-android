package com.susu.feature.received.ledgeredit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.susu.core.designsystem.component.appbar.SusuDefaultAppBar
import com.susu.core.designsystem.component.appbar.icon.BackIcon
import com.susu.core.designsystem.component.bottomsheet.datepicker.SusuLimitDatePickerBottomSheet
import com.susu.core.designsystem.component.button.AddConditionButton
import com.susu.core.designsystem.component.button.FilledButtonColor
import com.susu.core.designsystem.component.button.MediumButtonStyle
import com.susu.core.designsystem.component.button.SmallButtonStyle
import com.susu.core.designsystem.component.button.SusuFilledButton
import com.susu.core.designsystem.component.textfield.SusuBasicTextField
import com.susu.core.designsystem.component.textfieldbutton.SusuTextFieldWrapContentButton
import com.susu.core.designsystem.component.textfieldbutton.TextFieldButtonColor
import com.susu.core.designsystem.component.textfieldbutton.style.SmallTextFieldButtonStyle
import com.susu.core.designsystem.theme.Gray80
import com.susu.core.designsystem.theme.SusuTheme
import com.susu.core.ui.extension.collectWithLifecycle
import com.susu.core.ui.extension.susuClickable
import com.susu.core.ui.util.AnnotatedText
import com.susu.feature.received.R
import com.susu.feature.received.ledgeredit.component.LedgerEditContainer
import kotlinx.coroutines.android.awaitFrame
import kotlinx.coroutines.launch

@Composable
fun LedgerEditRoute(
    viewModel: LedgerEditViewModel = hiltViewModel(),
    popBackStack: () -> Unit,
    popBackStackWithLedger: (String) -> Unit,
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value

    val focusRequester = remember { FocusRequester() }
    val scope = rememberCoroutineScope()

    viewModel.sideEffect.collectWithLifecycle { sideEffect ->
        when (sideEffect) {
            LedgerEditSideEffect.PopBackStack -> popBackStack()
            is LedgerEditSideEffect.PopBackStackWithLedger -> popBackStackWithLedger(sideEffect.ledger)
            LedgerEditSideEffect.FocusCustomCategory -> scope.launch {
                awaitFrame()
                focusRequester.requestFocus()
            }
        }
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.initData()
    }

    LedgerEditScreen(
        uiState = uiState,
        onClickBack = viewModel::popBackStack,
        onClickCustomCategoryClearIcon = { viewModel.updateCustomCategory("") },
        onClickCustomCategoryCloseIcon = viewModel::hideCustomCategoryButton,
        onClickCustomCategoryInnerButton = viewModel::toggleCustomCategorySaved,
        onTextChangeCustomCategory = viewModel::updateCustomCategory,
        onClickAddConditionButton = viewModel::showCustomCategoryButton,
        onClickCategoryButton = viewModel::updateCategory,
        onTextChangeName = viewModel::updateName,
        focusRequester = focusRequester,
        onStartDateItemSelected = viewModel::updateStartYear,
        onClickStartDateText = viewModel::showStartDateBottomSheet,
        onDismissStartDateBottomSheet = viewModel::hideStartDateBottomSheet,
        onEndDateItemSelected = viewModel::updateEndYear,
        onClickEndDateText = viewModel::showEndDateBottomSheet,
        onDismissEndDateBottomSheet = viewModel::hideEndDateBottomSheet,
        onClickSaveButton = viewModel::editLedger,
    )
}

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun LedgerEditScreen(
    uiState: LedgerEditState = LedgerEditState(),
    focusRequester: FocusRequester = remember { FocusRequester() },
    onClickBack: () -> Unit = {},
    onClickCustomCategoryClearIcon: () -> Unit = {},
    onClickCustomCategoryCloseIcon: () -> Unit = {},
    onClickCustomCategoryInnerButton: () -> Unit = {},
    onClickAddConditionButton: () -> Unit = {},
    onClickCategoryButton: (Int) -> Unit = {},
    onTextChangeName: (String) -> Unit = {},
    onTextChangeCustomCategory: (String) -> Unit = {},
    onStartDateItemSelected: (Int, Int, Int) -> Unit = { _, _, _ -> },
    onClickStartDateText: () -> Unit = {},
    onDismissStartDateBottomSheet: () -> Unit = {},
    onEndDateItemSelected: (Int, Int, Int) -> Unit = { _, _, _ -> },
    onClickEndDateText: () -> Unit = {},
    onDismissEndDateBottomSheet: () -> Unit = {},
    onClickSaveButton: () -> Unit = {},
) {
    Box(
        modifier = Modifier
            .background(SusuTheme.colorScheme.background15)
            .fillMaxSize(),
    ) {
        Column(
            modifier = Modifier
                .background(SusuTheme.colorScheme.background15)
                .fillMaxSize(),
        ) {
            SusuDefaultAppBar(
                leftIcon = {
                    BackIcon(onClickBack)
                },
            )

            Spacer(modifier = Modifier.size(23.dp))

            Column(
                modifier = Modifier
                    .padding(horizontal = SusuTheme.spacing.spacing_m),
            ) {
                LedgerEditContainer(
                    name = stringResource(id = com.susu.core.ui.R.string.word_event_name),
                    verticalAlignment = Alignment.CenterVertically,
                    content = {
                        SusuBasicTextField(
                            text = uiState.name,
                            textStyle = SusuTheme.typography.title_m,
                            onTextChange = onTextChangeName,
                        )
                    },
                )

                LedgerEditContainer(
                    name = stringResource(id = com.susu.core.ui.R.string.word_category),
                    verticalAlignment = Alignment.Top,
                    content = {
                        FlowRow(
                            verticalArrangement = Arrangement.spacedBy(SusuTheme.spacing.spacing_xxs),
                            horizontalArrangement = Arrangement.spacedBy(SusuTheme.spacing.spacing_xxs),
                        ) {
                            uiState.categoryConfigList.dropLast(1).forEach { categoryConfig ->
                                SusuFilledButton(
                                    isActive = categoryConfig.id == uiState.selectedCategoryId,
                                    color = FilledButtonColor.Orange,
                                    style = SmallButtonStyle.height32,
                                    text = categoryConfig.name,
                                    onClick = { onClickCategoryButton(categoryConfig.id) },
                                )
                            }

                            if (uiState.showCustomCategoryButton) {
                                SusuTextFieldWrapContentButton(
                                    focusRequester = focusRequester,
                                    onTextChange = onTextChangeCustomCategory,
                                    color = TextFieldButtonColor.Orange,
                                    style = SmallTextFieldButtonStyle.height32,
                                    text = uiState.customCategory,
                                    isFocused = uiState.categoryConfigList.last().id == uiState.selectedCategoryId,
                                    isSaved = uiState.isCustomCategoryChipSaved,
                                    onClickClearIcon = onClickCustomCategoryClearIcon,
                                    onClickCloseIcon = onClickCustomCategoryCloseIcon,
                                    onClickFilledButton = onClickCustomCategoryInnerButton,
                                    onClickButton = { onClickCategoryButton(uiState.categoryConfigList.last().id) },
                                )
                            } else {
                                AddConditionButton(onClick = onClickAddConditionButton)
                            }
                        }
                    },
                )

                LedgerEditContainer(
                    name = stringResource(com.susu.core.ui.R.string.word_date),
                    verticalAlignment = Alignment.Top,
                    content = {
                        Column {
                            AnnotatedText(
                                modifier = Modifier.susuClickable(rippleEnabled = false, onClick = onClickStartDateText),
                                originalText = stringResource(
                                    R.string.ledger_edit_screen_from_date,
                                    uiState.startYear,
                                    uiState.startMonth,
                                    uiState.startDay,
                                ),
                                targetTextList = listOf(
                                    stringResource(R.string.ledger_edit_screen_year),
                                    stringResource(R.string.ledger_edit_screen_month),
                                    stringResource(R.string.ledger_edit_screen_from_day),
                                ),
                                originalTextStyle = SusuTheme.typography.title_m,
                                spanStyle = SusuTheme.typography.title_m.copy(Gray80).toSpanStyle(),
                            )
                            AnnotatedText(
                                modifier = Modifier.susuClickable(rippleEnabled = false, onClick = onClickEndDateText),
                                originalText = stringResource(
                                    R.string.ledger_edit_screen_until_date,
                                    uiState.endYear,
                                    uiState.endMonth,
                                    uiState.endDay,
                                ),
                                targetTextList = listOf(
                                    stringResource(R.string.ledger_edit_screen_year),
                                    stringResource(R.string.ledger_edit_screen_month),
                                    stringResource(R.string.ledger_edit_screen_until_day),
                                ),
                                originalTextStyle = SusuTheme.typography.title_m,
                                spanStyle = SusuTheme.typography.title_m.copy(Gray80).toSpanStyle(),
                            )
                        }
                    },
                )
            }
        }

        SusuFilledButton(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .imePadding(),
            shape = RectangleShape,
            isActive = uiState.saveButtonEnabled,
            isClickable = uiState.saveButtonEnabled,
            color = FilledButtonColor.Black,
            style = MediumButtonStyle.height60,
            text = stringResource(id = com.susu.core.ui.R.string.word_save),
            onClick = onClickSaveButton,
        )

        if (uiState.showStartDateBottomSheet) {
            SusuLimitDatePickerBottomSheet(
                initialYear = uiState.startYear,
                initialMonth = uiState.startMonth,
                initialDay = uiState.startDay,
                criteriaYear = uiState.endYear,
                criteriaMonth = uiState.endMonth,
                criteriaDay = uiState.endDay,
                afterDate = false,
                maximumContainerHeight = 346.dp,
                onDismissRequest = { _, _, _ -> onDismissStartDateBottomSheet() },
                onItemSelected = onStartDateItemSelected,
            )
        }

        if (uiState.showEndDateBottomSheet) {
            SusuLimitDatePickerBottomSheet(
                initialYear = uiState.endYear,
                initialMonth = uiState.endMonth,
                initialDay = uiState.endDay,
                criteriaYear = uiState.startYear,
                criteriaMonth = uiState.startMonth,
                criteriaDay = uiState.startDay,
                afterDate = true,
                maximumContainerHeight = 346.dp,
                onDismissRequest = { _, _, _ -> onDismissEndDateBottomSheet() },
                onItemSelected = onEndDateItemSelected,
            )
        }
    }
}

@Preview
@Composable
fun LedgerEditScreenPreview() {
    SusuTheme {
        LedgerEditScreen()
    }
}
