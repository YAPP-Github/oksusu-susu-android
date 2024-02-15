package com.susu.feature.envelopeedit

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.platform.LocalContext
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
import com.susu.core.designsystem.component.screen.LoadingScreen
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
import com.susu.core.ui.SnackbarToken
import com.susu.core.ui.extension.collectWithLifecycle
import com.susu.core.ui.extension.susuClickable
import com.susu.core.ui.util.to_yyyy_korYear_M_korMonth_d_korDay
import com.susu.feature.envelopeedit.component.EditDetailItem
import com.susu.feature.sent.R
import kotlinx.coroutines.android.awaitFrame
import kotlinx.coroutines.launch

@Composable
fun SentEnvelopeEditRoute(
    viewModel: SentEnvelopeEditViewModel = hiltViewModel(),
    popBackStack: () -> Unit,
    popBackStackWithEditedFriendId: (Long) -> Unit,
    onShowSnackbar: (SnackbarToken) -> Unit,
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    val categoryFocusRequester = remember { FocusRequester() }
    val relationshipFocusRequester = remember { FocusRequester() }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    viewModel.sideEffect.collectWithLifecycle { sideEffect ->
        when (sideEffect) {
            SentEnvelopeEditSideEffect.PopBackStack -> popBackStack()
            is SentEnvelopeEditSideEffect.HandleException -> {}
            SentEnvelopeEditSideEffect.FocusCustomCategory -> {
                scope.launch {
                    awaitFrame()
                    categoryFocusRequester.requestFocus()
                }
            }

            SentEnvelopeEditSideEffect.FocusCustomRelationship -> {
                scope.launch {
                    awaitFrame()
                    relationshipFocusRequester.requestFocus()
                }
            }

            is SentEnvelopeEditSideEffect.PopBackStackWithEditedFriendId -> popBackStackWithEditedFriendId(sideEffect.id)
            SentEnvelopeEditSideEffect.ShowCategoryNotValidSnackbar -> onShowSnackbar(
                SnackbarToken(
                    message = context.getString(R.string.sent_snackbar_category_validation),
                ),
            )

            SentEnvelopeEditSideEffect.ShowMemoNotValidSnackbar -> onShowSnackbar(
                SnackbarToken(
                    message = context.getString(R.string.sent_snackbar_memo_validation),
                ),
            )

            SentEnvelopeEditSideEffect.ShowMoneyNotValidSnackbar -> onShowSnackbar(
                SnackbarToken(
                    message = context.getString(R.string.sent_snackbar_money_validation),
                ),
            )

            SentEnvelopeEditSideEffect.ShowNameNotValidSnackbar -> onShowSnackbar(
                SnackbarToken(
                    message = context.getString(R.string.sent_snackbar_name_validation),
                ),
            )

            SentEnvelopeEditSideEffect.ShowPhoneNotValidSnackbar -> onShowSnackbar(
                SnackbarToken(
                    message = context.getString(R.string.sent_snackbar_phone_validation),
                ),
            )

            SentEnvelopeEditSideEffect.ShowPresentNotValidSnackbar -> onShowSnackbar(
                SnackbarToken(
                    message = context.getString(R.string.sent_snackbar_present_validation),
                ),
            )

            SentEnvelopeEditSideEffect.ShowRelationshipNotValidSnackbar -> onShowSnackbar(
                SnackbarToken(
                    message = context.getString(R.string.sent_snackbar_relationship_validation),
                ),
            )
        }
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.run {
            initData()
        }
    }

    BackHandler {
        popBackStack()
    }

    SentEnvelopeEditScreen(
        uiState = uiState,
        categoryFocusRequester = categoryFocusRequester,
        relationshipFocusRequester = relationshipFocusRequester,
        onClickBackIcon = viewModel::popBackStack,
        onClickSave = viewModel::editEnvelope,
        onClickCustomCategoryAdd = viewModel::showCustomCategoryInput,
        onClickCustomRelationshipAdd = viewModel::showCustomRelationshipInput,
        onClickDateText = viewModel::showDatePickerSheet,
        onClickCustomCategoryInnerButton = viewModel::toggleCustomCategoryInputSaved,
        onClickCustomRelationshipInnerButton = viewModel::toggleCustomRelationshipInputSaved,
        onCloseCustomCategory = viewModel::hideCustomCategoryInput,
        onCloseCustomRelationship = viewModel::hideCustomRelationshipInput,
        onSelectCategory = { viewModel.updateCategoryId(it.id) },
        onSelectRelationship = { viewModel.updateRelationshipId(it.id) },
        onMoneyUpdated = viewModel::updateAmount,
        onFriendNameUpdated = viewModel::updateFriendName,
        onCustomCategoryUpdated = viewModel::updateCustomCategory,
        onCustomCategoryCleared = { viewModel.updateCustomCategory("") },
        onCustomRelationshipUpdated = viewModel::updateCustomRelationship,
        onCustomRelationshipCleared = { viewModel.updateCustomRelationship("") },
        onDatePickerSheetDismissed = viewModel::hideDatePickerSheet,
        onDateUpdated = viewModel::updateHandedOverAt,
        onHasVisitedUpdated = viewModel::updateHasVisited,
        onPhoneNumberUpdated = viewModel::updatePhoneNumber,
        onGiftUpdated = viewModel::updateGift,
        onMemoUpdated = viewModel::updateMemo,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SentEnvelopeEditScreen(
    modifier: Modifier = Modifier,
    uiState: SentEnvelopeEditState = SentEnvelopeEditState(),
    categoryFocusRequester: FocusRequester = remember { FocusRequester() },
    relationshipFocusRequester: FocusRequester = remember { FocusRequester() },
    onClickBackIcon: () -> Unit = {},
    onClickSave: () -> Unit = {},
    onMoneyUpdated: (Long) -> Unit = {},
    onSelectCategory: (Category) -> Unit = {},
    onClickCustomCategoryAdd: () -> Unit = {},
    onCustomCategoryUpdated: (String) -> Unit = {},
    onCustomCategoryCleared: () -> Unit = {},
    onCloseCustomCategory: () -> Unit = {},
    onClickCustomCategoryInnerButton: () -> Unit = {},
    onFriendNameUpdated: (String) -> Unit = {},
    onSelectRelationship: (Relationship) -> Unit = {},
    onClickCustomRelationshipAdd: () -> Unit = {},
    onCustomRelationshipUpdated: (String) -> Unit = {},
    onCustomRelationshipCleared: () -> Unit = {},
    onCloseCustomRelationship: () -> Unit = {},
    onClickCustomRelationshipInnerButton: () -> Unit = {},
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
                    maxLines = 2,
                )
                Spacer(modifier = Modifier.size(SusuTheme.spacing.spacing_m))
                EditDetailItem(
                    categoryText = stringResource(com.susu.core.ui.R.string.word_event),
                    categoryTextAlign = Alignment.Top,
                ) {
                    uiState.categoryConfig.dropLast(1).forEach { category ->
                        SusuFilledButton(
                            color = FilledButtonColor.Orange,
                            style = SmallButtonStyle.height32,
                            text = category.name,
                            isActive = category.id == uiState.categoryId,
                            onClick = { onSelectCategory(category) },
                        )
                    }
                    if (uiState.showCustomCategory) {
                        SusuTextFieldWrapContentButton(
                            focusRequester = categoryFocusRequester,
                            color = TextFieldButtonColor.Orange,
                            style = SmallTextFieldButtonStyle.height32,
                            text = uiState.customCategory ?: "",
                            isFocused = uiState.categoryId == uiState.categoryConfig.last().id,
                            isSaved = uiState.customCategorySaved,
                            onTextChange = onCustomCategoryUpdated,
                            onClickClearIcon = onCustomCategoryCleared,
                            onClickCloseIcon = onCloseCustomCategory,
                            onClickFilledButton = onClickCustomCategoryInnerButton,
                            onClickButton = { onSelectCategory(uiState.categoryConfig.last()) },
                        )
                    } else {
                        AddConditionButton(
                            onClick = onClickCustomCategoryAdd,
                        )
                    }
                }
                EditDetailItem(
                    categoryText = stringResource(com.susu.core.ui.R.string.word_name),
                    categoryTextAlign = Alignment.CenterVertically,
                ) {
                    SusuBasicTextField(
                        text = uiState.friendName,
                        onTextChange = onFriendNameUpdated,
                        placeholder = stringResource(R.string.sent_envelope_edit_category_name_placeholder),
                        placeholderColor = Gray30,
                        textStyle = SusuTheme.typography.title_s,
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 2,
                    )
                }
                EditDetailItem(
                    categoryText = stringResource(com.susu.core.ui.R.string.word_relationship),
                    categoryTextAlign = Alignment.Top,
                ) {
                    uiState.relationshipConfig.dropLast(1).forEach { relationship ->
                        SusuFilledButton(
                            color = FilledButtonColor.Orange,
                            style = SmallButtonStyle.height32,
                            text = relationship.relation,
                            isActive = relationship.id == uiState.relationshipId,
                            onClick = { onSelectRelationship(relationship) },
                        )
                    }
                    if (uiState.showCustomRelationship) {
                        SusuTextFieldWrapContentButton(
                            focusRequester = relationshipFocusRequester,
                            style = SmallTextFieldButtonStyle.height32,
                            color = TextFieldButtonColor.Orange,
                            text = uiState.customRelationship ?: "",
                            isFocused = uiState.relationshipId == uiState.relationshipConfig.last().id,
                            isSaved = uiState.customRelationshipSaved,
                            onTextChange = onCustomRelationshipUpdated,
                            onClickClearIcon = onCustomRelationshipCleared,
                            onClickCloseIcon = onCloseCustomRelationship,
                            onClickFilledButton = onClickCustomRelationshipInnerButton,
                            onClickButton = { onSelectRelationship(uiState.relationshipConfig.last()) },
                        )
                    } else {
                        AddConditionButton(
                            onClick = onClickCustomRelationshipAdd,
                        )
                    }
                }
                EditDetailItem(
                    categoryText = stringResource(com.susu.core.ui.R.string.word_date),
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
                    categoryText = stringResource(com.susu.core.ui.R.string.word_is_visited),
                    categoryTextAlign = Alignment.Top,
                ) {
                    SusuFilledButton(
                        color = FilledButtonColor.Orange,
                        style = SmallButtonStyle.height32,
                        text = stringResource(com.susu.core.ui.R.string.word_yes),
                        isActive = uiState.hasVisited == true,
                        onClick = { onHasVisitedUpdated(true) },
                        modifier = Modifier.weight(1f),
                    )
                    SusuFilledButton(
                        color = FilledButtonColor.Orange,
                        style = SmallButtonStyle.height32,
                        text = stringResource(com.susu.core.ui.R.string.word_no),
                        isActive = uiState.hasVisited == false,
                        onClick = { onHasVisitedUpdated(false) },
                        modifier = Modifier.weight(1f),
                    )
                }
                EditDetailItem(
                    categoryText = stringResource(com.susu.core.ui.R.string.word_gift),
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
                        maxLines = 3,
                    )
                }
                EditDetailItem(
                    categoryText = stringResource(com.susu.core.ui.R.string.word_phone_number),
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
                        maxLines = 2,
                    )
                }
                EditDetailItem(
                    categoryText = stringResource(com.susu.core.ui.R.string.word_memo),
                    categoryTextColor = if (uiState.memo != null) Gray70 else Gray40,
                    categoryTextAlign = Alignment.Top,
                ) {
                    SusuBasicTextField(
                        text = uiState.memo ?: "",
                        onTextChange = onMemoUpdated,
                        placeholder = stringResource(R.string.sent_envelope_edit_category_memo_placeholder),
                        placeholderColor = Gray30,
                        textStyle = SusuTheme.typography.title_s,
                        maxLines = 3,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
                Spacer(modifier = Modifier.height(SusuTheme.spacing.spacing_xxxl))
            }
            SusuFilledButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .imePadding(),
                color = FilledButtonColor.Black,
                style = MediumButtonStyle.height60,
                shape = RectangleShape,
                text = stringResource(com.susu.core.ui.R.string.word_save),
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

        if (uiState.isLoading) {
            LoadingScreen()
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
