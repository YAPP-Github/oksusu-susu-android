package com.susu.feature.received.envelopeedit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.susu.core.designsystem.component.appbar.SusuDefaultAppBar
import com.susu.core.designsystem.component.appbar.icon.BackIcon
import com.susu.core.designsystem.component.bottomsheet.datepicker.SusuDatePickerBottomSheet
import com.susu.core.designsystem.component.button.AddConditionButton
import com.susu.core.designsystem.component.button.FilledButtonColor
import com.susu.core.designsystem.component.button.MediumButtonStyle
import com.susu.core.designsystem.component.button.SmallButtonStyle
import com.susu.core.designsystem.component.button.SusuFilledButton
import com.susu.core.designsystem.component.textfield.SusuBasicTextField
import com.susu.core.designsystem.component.textfield.SusuPriceTextField
import com.susu.core.designsystem.component.textfieldbutton.SusuTextFieldWrapContentButton
import com.susu.core.designsystem.component.textfieldbutton.TextFieldButtonColor
import com.susu.core.designsystem.component.textfieldbutton.style.SmallTextFieldButtonStyle
import com.susu.core.designsystem.theme.Gray30
import com.susu.core.designsystem.theme.Gray40
import com.susu.core.designsystem.theme.Gray70
import com.susu.core.designsystem.theme.SusuTheme
import com.susu.core.model.Relationship
import com.susu.core.ui.extension.collectWithLifecycle
import com.susu.core.ui.extension.susuClickable
import com.susu.feature.received.R
import com.susu.feature.received.envelopeedit.component.EditDetailItem
import kotlinx.coroutines.android.awaitFrame
import kotlinx.coroutines.launch

@Composable
fun ReceivedEnvelopeEditRoute(
    viewModel: ReceivedEnvelopeEditViewModel = hiltViewModel(),
    popBackStack: () -> Unit,
    handleException: (Throwable, () -> Unit) -> Unit,
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value

    val focusRequester = remember { FocusRequester() }
    val scope = rememberCoroutineScope()

    viewModel.sideEffect.collectWithLifecycle { sideEffect ->
        when (sideEffect) {
            is ReceivedEnvelopeEditSideEffect.HandleException -> handleException(
                sideEffect.throwable,
                sideEffect.retry,
            )

            ReceivedEnvelopeEditSideEffect.PopBackStack -> popBackStack()
            ReceivedEnvelopeEditSideEffect.FocusCustomRelation -> scope.launch {
                awaitFrame()
                focusRequester.requestFocus()
            }
        }
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.initData()
    }


    ReceivedEnvelopeEditScreen(
        uiState = uiState,
        focusRequester = focusRequester,
        onClickBackIcon = viewModel::popBackStack,
        onClickSave = viewModel::editReceivedEnvelope,
        onTextChangeMoney = viewModel::updateMoney,
        onTextChangeName = viewModel::updateName,
        onTextChangeRelation = viewModel::updateCustomRelation,
        onClickRelation = viewModel::updateRelation,
        onClickCustomRelationClear = { viewModel.updateCustomRelation("") },
        onClickCustomRelationClose = viewModel::closeCustomRelation,
        onClickRelationInnerButton = viewModel::toggleRelationSaved,
        onClickAddConditionButton = viewModel::showCustomRelation,
        onClickDate = viewModel::showDateBottomSheet,
        onClickHasVisited = viewModel::updateHasVisited,
        onTextChangeGift = viewModel::updateGift,
        onTextChangePhoneNumber = viewModel::updatePhoneNumber,
        onTextChangeMemo = viewModel::updateMemo,
        onDismissDateBottomSheet = viewModel::hideDateBottomSheet,
        onItemSelectedDateBottomSheet = viewModel::updateDate,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReceivedEnvelopeEditScreen(
    uiState: ReceivedEnvelopeEditState = ReceivedEnvelopeEditState(),
    focusRequester: FocusRequester = remember { FocusRequester() },
    onClickBackIcon: () -> Unit = {},
    onClickSave: () -> Unit = {},
    onTextChangeMoney: (String) -> Unit = {},
    onTextChangeName: (String) -> Unit = {},
    onTextChangeRelation: (String) -> Unit = {},
    onClickRelation: (Relationship) -> Unit = {},
    onClickCustomRelationClear: () -> Unit = {},
    onClickCustomRelationClose: () -> Unit = {},
    onClickRelationInnerButton: () -> Unit = {},
    onClickAddConditionButton: () -> Unit = {},
    onClickDate: () -> Unit = {},
    onClickHasVisited: (Boolean) -> Unit = {},
    onTextChangeGift: (String) -> Unit = {},
    onTextChangePhoneNumber: (String) -> Unit = {},
    onTextChangeMemo: (String) -> Unit = {},
    onDismissDateBottomSheet: (Int, Int, Int) -> Unit = { _, _, _ -> },
    onItemSelectedDateBottomSheet: (Int, Int, Int) -> Unit = { _, _, _ -> },
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(SusuTheme.colorScheme.background10),
    ) {
        Column {
            SusuDefaultAppBar(
                leftIcon = {
                    BackIcon(
                        onClick = onClickBackIcon,
                    )
                },
            )
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .weight(1f)
                    .padding(
                        start = SusuTheme.spacing.spacing_m,
                        end = SusuTheme.spacing.spacing_m,
                        top = SusuTheme.spacing.spacing_xl,
                    ),
            ) {
                SusuPriceTextField(
                    text = uiState.envelope.amount.toString(),
                    onTextChange = onTextChangeMoney,
                    textStyle = SusuTheme.typography.title_xxl,
                    modifier = Modifier.fillMaxWidth(),
                )
                Spacer(modifier = Modifier.size(SusuTheme.spacing.spacing_m))
                EditDetailItem(
                    categoryText = stringResource(R.string.received_envelope_edit_screen_name),
                    categoryTextAlign = Alignment.CenterVertically,
                ) {
                    SusuBasicTextField(
                        text = uiState.envelope.friend.name,
                        onTextChange = onTextChangeName,
                        placeholder = stringResource(R.string.received_envelope_edit_screen_name_placeholder),
                        placeholderColor = Gray30,
                        textStyle = SusuTheme.typography.title_s,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
                EditDetailItem(
                    categoryText = stringResource(R.string.received_envelope_edit_screen_relationship),
                    categoryTextAlign = Alignment.Top,
                ) {
                    uiState.relationshipConfig.dropLast(1).forEach {
                        SusuFilledButton(
                            color = FilledButtonColor.Orange,
                            style = SmallButtonStyle.height32,
                            text = it.relation,
                            isActive = it == uiState.envelope.relationship,
                            onClick = { onClickRelation(it) },
                        )
                    }

                    if (uiState.showCustomRelationButton) {
                        SusuTextFieldWrapContentButton(
                            focusRequester = focusRequester,
                            onTextChange = onTextChangeRelation,
                            color = TextFieldButtonColor.Orange,
                            style = SmallTextFieldButtonStyle.height32,
                            text = uiState.relationshipConfig.last().customRelation ?: "",
                            isFocused = uiState.relationshipConfig.last().id == uiState.envelope.relationship.id,
                            isSaved = uiState.isRelationSaved,
                            onClickClearIcon = onClickCustomRelationClear,
                            onClickCloseIcon = onClickCustomRelationClose,
                            onClickFilledButton = onClickRelationInnerButton,
                            onClickButton = { onClickRelation(uiState.relationshipConfig.last()) },
                        )
                    } else {
                        AddConditionButton(onClick = onClickAddConditionButton)
                    }
                }
                EditDetailItem(categoryText = stringResource(id = com.susu.core.ui.R.string.word_date), categoryTextAlign = Alignment.Top) {
                    Text(
                        modifier = Modifier.susuClickable(rippleEnabled = false, onClick = onClickDate),
                        text = stringResource(
                            R.string.ledger_add_screen_date,
                            uiState.envelope.handedOverAt.year,
                            uiState.envelope.handedOverAt.month.value,
                            uiState.envelope.handedOverAt.dayOfMonth,
                        ),
                        style = SusuTheme.typography.title_m,
                    )
                }
                EditDetailItem(
                    categoryText = stringResource(R.string.received_envelope_edit_screen_visited),
                    categoryTextAlign = Alignment.Top,
                ) {
                    SusuFilledButton(
                        color = FilledButtonColor.Orange,
                        style = SmallButtonStyle.height32,
                        text = stringResource(com.susu.core.ui.R.string.word_yes),
                        isActive = uiState.envelope.hasVisited == true,
                        onClick = { onClickHasVisited(true) },
                        modifier = Modifier.weight(1f),
                    )
                    SusuFilledButton(
                        color = FilledButtonColor.Orange,
                        style = SmallButtonStyle.height32,
                        text = stringResource(com.susu.core.ui.R.string.word_no),
                        isActive = uiState.envelope.hasVisited == false,
                        onClick = { onClickHasVisited(false) },
                        modifier = Modifier.weight(1f),
                    )
                }
                EditDetailItem(
                    categoryText = stringResource(R.string.received_envelope_edit_screen_present),
                    categoryTextColor = if (!uiState.envelope.gift.isNullOrEmpty()) Gray70 else Gray40,
                    categoryTextAlign = Alignment.CenterVertically,
                ) {
                    SusuBasicTextField(
                        text = uiState.envelope.gift ?: "",
                        onTextChange = onTextChangeGift,
                        placeholder = stringResource(R.string.received_envelope_edit_screen_present_placeholder),
                        placeholderColor = Gray30,
                        textStyle = SusuTheme.typography.title_s,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
                EditDetailItem(
                    categoryText = stringResource(com.susu.core.ui.R.string.word_phone_number),
                    categoryTextColor = if (uiState.envelope.friend.phoneNumber.isNotEmpty()) Gray70 else Gray40,
                    categoryTextAlign = Alignment.CenterVertically,
                ) {
                    SusuBasicTextField(
                        text = uiState.envelope.friend.phoneNumber,
                        onTextChange = onTextChangePhoneNumber,
                        placeholder = stringResource(R.string.received_envelope_edit_screen_phone_number_placeholder),
                        placeholderColor = Gray30,
                        textStyle = SusuTheme.typography.title_s,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
                EditDetailItem(
                    categoryText = stringResource(com.susu.core.ui.R.string.word_memo),
                    categoryTextColor = if (!uiState.envelope.memo.isNullOrEmpty()) Gray70 else Gray40,
                    categoryTextAlign = Alignment.Top,
                ) {
                    SusuBasicTextField(
                        text = uiState.envelope.memo ?: "",
                        onTextChange = onTextChangeMemo,
                        placeholder = stringResource(R.string.received_envelope_edit_screen_memo_placeholder),
                        placeholderColor = Gray30,
                        textStyle = SusuTheme.typography.title_s,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
                Spacer(modifier = Modifier.size(240.dp))
            }

            if (uiState.showDateBottomSheet) {
                SusuDatePickerBottomSheet(
                    initialYear = uiState.envelope.handedOverAt.year,
                    initialMonth = uiState.envelope.handedOverAt.month.value,
                    initialDay = uiState.envelope.handedOverAt.dayOfMonth,
                    maximumContainerHeight = 346.dp,
                    onDismissRequest = onDismissDateBottomSheet,
                    onItemSelected = onItemSelectedDateBottomSheet,
                )
            }

            SusuFilledButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .imePadding(),
                color = FilledButtonColor.Black,
                style = MediumButtonStyle.height60,
                shape = RectangleShape,
                isActive = uiState.buttonEnabled,
                isClickable = uiState.buttonEnabled,
                text = stringResource(com.susu.core.ui.R.string.word_save),
                onClick = onClickSave,
            )
        }
    }
}

@Preview
@Composable
fun ReceivedEnvelopeEditScreenPreview() {
    SusuTheme {
        ReceivedEnvelopeEditScreen()
    }
}
