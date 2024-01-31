package com.susu.feature.envelopeedit

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.susu.core.designsystem.theme.Gray100
import com.susu.core.designsystem.theme.Gray30
import com.susu.core.designsystem.theme.Gray40
import com.susu.core.designsystem.theme.Gray70
import com.susu.core.designsystem.theme.SusuTheme
import com.susu.core.model.Category
import com.susu.core.model.Relationship
import com.susu.core.ui.extension.collectWithLifecycle
import com.susu.core.ui.extension.susuClickable
import com.susu.core.ui.util.to_yyyy_korYear_M_korMonth_d_korDay
import com.susu.feature.envelopeedit.component.EditDetailItem
import com.susu.feature.sent.R

@Composable
fun SentEnvelopeEditRoute(
    viewModel: SentEnvelopeEditViewModel = hiltViewModel(),
    popBackStack: () -> Unit,
    navigateSentEnvelopeDetail: () -> Unit,
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value

    viewModel.sideEffect.collectWithLifecycle { sideEffect ->
        when (sideEffect) {
            SentEnvelopeEditSideEffect.PopBackStack -> popBackStack()
            is SentEnvelopeEditSideEffect.HandleException -> {}
        }
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.run {
            initData()
            getEnvelopConfig()
        }
    }

    SentEnvelopeEditScreen(
        uiState = uiState,
        onClickBackIcon = viewModel::popBackStack,
        onClickSave = navigateSentEnvelopeDetail,
        onMoneyUpdated = viewModel::updateAmount,
        onSelectCategory = { viewModel.updateCategoryId(it.id) },
        onClickCustomCategoryAdd = viewModel::showCustomCategoryInput,
        onCustomCategoryUpdated = viewModel::updateCustomCategory,
        onCustomCategoryCleared = { viewModel.updateCustomCategory("") },
        onFriendNameUpdated = viewModel::updateFriendName,
        onSelectRelationship = { viewModel.updateRelationshipId(it.id) },
        onClickCustomRelationshipAdd = viewModel::showCustomRelationshipInput,
        onCustomRelationshipUpdated = viewModel::updateCustomRelationship,
        onCustomRelationshipCleared = { viewModel.updateCustomRelationship("") },
        onClickDateText = viewModel::showDatePickerSheet,
        onHasVisitedUpdated = viewModel::updateHasVisited,
        onGiftUpdated = viewModel::updateGift,
        onMemoUpdated = viewModel::updateMemo,
        onPhoneNumberUpdated = viewModel::updatePhoneNumber,
        onDateUpdated = viewModel::updateHandedOverAt,
        onDatePickerSheetDismissed = viewModel::hideDatePickerSheet,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SentEnvelopeEditScreen(
    modifier: Modifier = Modifier,
    uiState: SentEnvelopeEditState = SentEnvelopeEditState(),
    onClickBackIcon: () -> Unit = {},
    onClickSave: () -> Unit = {},
    onMoneyUpdated: (Long) -> Unit = {},
    onSelectCategory: (Category) -> Unit = {},
    onClickCustomCategoryAdd: () -> Unit = {},
    onCustomCategoryUpdated: (String) -> Unit = {},
    onCustomCategoryCleared: () -> Unit = {},
    onFriendNameUpdated: (String) -> Unit = {},
    onSelectRelationship: (Relationship) -> Unit = {},
    onClickCustomRelationshipAdd: () -> Unit = {},
    onCustomRelationshipUpdated: (String) -> Unit = {},
    onCustomRelationshipCleared: () -> Unit = {},
    onClickDateText: () -> Unit = {},
    onHasVisitedUpdated: (Boolean) -> Unit = {},
    onGiftUpdated: (String) -> Unit = {},
    onMemoUpdated: (String) -> Unit = {},
    onPhoneNumberUpdated: (String) -> Unit = {},
    onDateUpdated: (year: Int, month: Int, day: Int) -> Unit = { _, _, _ -> },
    onDatePickerSheetDismissed: () -> Unit = {},
) {
    Box(
        modifier = modifier
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
                    text = uiState.amount.toString(),
                    onTextChange = { onMoneyUpdated(it.toLongOrNull() ?: 0L) },
                    textStyle = SusuTheme.typography.title_xxl,
                    modifier = Modifier.fillMaxWidth(),
                )
                Spacer(modifier = Modifier.size(SusuTheme.spacing.spacing_m))
                EditDetailItem(
                    categoryText = stringResource(R.string.sent_envelope_edit_category_event),
                    categoryTextAlign = Alignment.Top,
                ) {
                    uiState.categoryConfig.forEach { category ->
                        SusuFilledButton(
                            color = FilledButtonColor.Orange,
                            style = SmallButtonStyle.height32,
                            text = category.name,
                            isActive = category.id == uiState.categoryId,
                            onClick = { onSelectCategory(category) },
                        )
                    }
                    AddConditionButton(
                        onClick = onClickCustomCategoryAdd,
                    )
                    if (uiState.showCustomCategory) {
                        SusuTextFieldWrapContentButton(
                            style = SmallTextFieldButtonStyle.height32,
                            color = TextFieldButtonColor.Orange,
                            text = uiState.customCategory ?: "",
                            onTextChange = onCustomCategoryUpdated,
                            onClickClearIcon = onCustomCategoryCleared,
                            onClickCloseIcon = { /* 무슨 동작을 해야하지?? */ },
                        )
                    }
                }
                EditDetailItem(
                    categoryText = stringResource(R.string.sent_envelope_edit_category_name),
                    categoryTextAlign = Alignment.CenterVertically,
                ) {
                    SusuBasicTextField(
                        text = uiState.friendName,
                        onTextChange = onFriendNameUpdated,
                        placeholder = stringResource(R.string.sent_envelope_edit_category_name_placeholder),
                        placeholderColor = Gray30,
                        textStyle = SusuTheme.typography.title_s,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
                EditDetailItem(
                    categoryText = stringResource(R.string.sent_envelope_edit_category_relationship),
                    categoryTextAlign = Alignment.Top,
                ) {
                    uiState.relationshipConfig.forEach { relationship ->
                        SusuFilledButton(
                            color = FilledButtonColor.Orange,
                            style = SmallButtonStyle.height32,
                            text = relationship.relation,
                            isActive = relationship.id == uiState.relationshipId,
                            onClick = { onSelectRelationship(relationship) },
                        )
                    }
                    AddConditionButton(
                        onClick = onClickCustomRelationshipAdd,
                    )
                    if (uiState.showCustomRelationship) {
                        SusuTextFieldWrapContentButton(
                            style = SmallTextFieldButtonStyle.height32,
                            color = TextFieldButtonColor.Orange,
                            text = uiState.customRelationship ?: "",
                            onTextChange = onCustomRelationshipUpdated,
                            onClickClearIcon = onCustomRelationshipCleared,
                            onClickCloseIcon = { /* 무슨 동작을 해야하지?? */ },
                        )
                    }
                }
                EditDetailItem(
                    categoryText = stringResource(R.string.sent_envelope_edit_category_date),
                    categoryTextAlign = Alignment.CenterVertically,
                ) {
                    Text(
                        text = uiState.handedOverAt.to_yyyy_korYear_M_korMonth_d_korDay(),
                        style = SusuTheme.typography.title_s,
                        color = Gray100,
                        modifier = Modifier
                            .fillMaxWidth()
                            .susuClickable(
                                rippleEnabled = false,
                                onClick = onClickDateText,
                            ),
                    )
                }
                EditDetailItem(
                    categoryText = stringResource(R.string.sent_envelope_edit_category_visited),
                    categoryTextAlign = Alignment.Top,
                ) {
                    SusuFilledButton(
                        color = FilledButtonColor.Orange,
                        style = SmallButtonStyle.height32,
                        text = stringResource(R.string.sent_envelope_edit_category_visited_yes),
                        isActive = uiState.hasVisited == true,
                        onClick = { onHasVisitedUpdated(true) },
                        modifier = Modifier.weight(1f),
                    )
                    SusuFilledButton(
                        color = FilledButtonColor.Orange,
                        style = SmallButtonStyle.height32,
                        text = stringResource(R.string.sent_envelope_edit_category_visited_no),
                        isActive = uiState.hasVisited == false,
                        onClick = { onHasVisitedUpdated(false) },
                        modifier = Modifier.weight(1f),
                    )
                }
                EditDetailItem(
                    categoryText = stringResource(R.string.sent_envelope_edit_category_present),
                    categoryTextColor = if (uiState.gift != null) Gray70 else Gray40,
                    categoryTextAlign = Alignment.CenterVertically,
                ) {
                    SusuBasicTextField(
                        text = uiState.gift ?: "",
                        onTextChange = onGiftUpdated,
                        placeholder = stringResource(R.string.sent_envelope_edit_category_present_placeholder),
                        placeholderColor = Gray30,
                        textStyle = SusuTheme.typography.title_s,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
                EditDetailItem(
                    categoryText = stringResource(R.string.sent_envelope_edit_category_phone),
                    categoryTextColor = if (uiState.phoneNumber != null) Gray70 else Gray40,
                    categoryTextAlign = Alignment.CenterVertically,
                ) {
                    SusuBasicTextField(
                        text = uiState.phoneNumber ?: "",
                        onTextChange = onPhoneNumberUpdated,
                        placeholder = stringResource(R.string.sent_envelope_edit_category_phone_placeholder),
                        placeholderColor = Gray30,
                        textStyle = SusuTheme.typography.title_s,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
                EditDetailItem(
                    categoryText = stringResource(R.string.sent_envelope_edit_category_memo),
                    categoryTextColor = if (uiState.memo != null) Gray70 else Gray40,
                    categoryTextAlign = Alignment.Top,
                ) {
                    SusuBasicTextField(
                        text = uiState.memo ?: "",
                        onTextChange = onMemoUpdated,
                        placeholder = stringResource(R.string.sent_envelope_edit_category_memo_placeholder),
                        placeholderColor = Gray30,
                        textStyle = SusuTheme.typography.title_s,
                        maxLines = 2,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
                Spacer(modifier = Modifier.size(240.dp))
            }

            SusuFilledButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .imePadding(),
                color = FilledButtonColor.Black,
                style = MediumButtonStyle.height60,
                shape = RectangleShape,
                text = stringResource(R.string.sent_envelope_edit_save),
                onClick = onClickSave,
            )
        }

        if (uiState.showDatePickerSheet) {
            SusuDatePickerBottomSheet(
                maximumContainerHeight = 346.dp,
                initialYear = uiState.handedOverAt.year,
                initialMonth = uiState.handedOverAt.monthValue,
                initialDay = uiState.handedOverAt.dayOfMonth,
                onDismissRequest = { year, month, day ->
                    onDateUpdated(year, month, day)
                    onDatePickerSheetDismissed()
                },
                onItemSelected = { year, month, day -> onDateUpdated(year, month, day) },
            )
        }
    }
}

@Preview
@Composable
fun SentEnvelopeEditScreenPreview() {
    SusuTheme {
        SentEnvelopeEditScreen()
    }
}
