package com.susu.feature.envelopeedit

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.susu.core.designsystem.component.appbar.SusuDefaultAppBar
import com.susu.core.designsystem.component.appbar.icon.BackIcon
import com.susu.core.designsystem.component.bottomsheet.datepicker.SusuDatePickerBottomSheet
import com.susu.core.designsystem.component.button.AddConditionButton
import com.susu.core.designsystem.component.button.FilledButtonColor
import com.susu.core.designsystem.component.button.MediumButtonStyle
import com.susu.core.designsystem.component.button.SmallButtonStyle
import com.susu.core.designsystem.component.button.SusuFilledButton
import com.susu.core.designsystem.component.textfield.SusuBasicTextField
import com.susu.core.designsystem.theme.Gray100
import com.susu.core.designsystem.theme.Gray30
import com.susu.core.designsystem.theme.Gray40
import com.susu.core.designsystem.theme.Gray70
import com.susu.core.designsystem.theme.SusuTheme
import com.susu.core.ui.extension.collectWithLifecycle
import com.susu.feature.envelopeedit.component.EditDetailItem
import com.susu.feature.sent.R

@Composable
fun SentEnvelopeEditRoute(
    viewModel: SentEnvelopeEditViewModel = hiltViewModel(),
    popBackStack: () -> Unit,
    navigateSentEnvelopeDetail: () -> Unit,
) {
    viewModel.sideEffect.collectWithLifecycle { sideEffect ->
        when (sideEffect) {
            SentEnvelopeEditSideEffect.PopBackStack -> popBackStack()
        }
    }

    SentEnvelopeEditScreen(
        onClickBackIcon = viewModel::popBackStack,
        onClickSave = navigateSentEnvelopeDetail,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SentEnvelopeEditScreen(
    modifier: Modifier = Modifier,
    onClickBackIcon: () -> Unit = {},
    onClickSave: () -> Unit = {},
) {
    // TODO: 수정 필요
    var money by remember { mutableStateOf(150000) }
    var name by remember { mutableStateOf("김철수") }
    var present by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var memo by remember { mutableStateOf("") }
    var isSheetOpen by remember { mutableStateOf(false) }

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
                modifier = modifier
                    .verticalScroll(rememberScrollState())
                    .weight(1f)
                    .padding(
                        start = SusuTheme.spacing.spacing_m,
                        end = SusuTheme.spacing.spacing_m,
                        top = SusuTheme.spacing.spacing_xl,
                    ),
            ) {
                SusuBasicTextField(
                    text = "${money}원",
                    onTextChange = { money = it.toInt() },
                    textStyle = SusuTheme.typography.title_xxl,
                    modifier = modifier.fillMaxWidth(),
                )
                Spacer(modifier = modifier.size(SusuTheme.spacing.spacing_m))
                EditDetailItem(
                    categoryText = stringResource(R.string.sent_envelope_edit_category_event),
                    categoryTextAlign = Alignment.Top,
                ) {
                    SusuFilledButton(
                        color = FilledButtonColor.Orange,
                        style = SmallButtonStyle.height32,
                        text = stringResource(R.string.sent_envelope_edit_category_event_wedding),
                        isActive = true,
                        onClick = {},
                    )
                    SusuFilledButton(
                        color = FilledButtonColor.Orange,
                        style = SmallButtonStyle.height32,
                        text = stringResource(R.string.sent_envelope_edit_category_event_first_birth),
                        isActive = false,
                        onClick = {},
                    )
                    SusuFilledButton(
                        color = FilledButtonColor.Orange,
                        style = SmallButtonStyle.height32,
                        text = stringResource(R.string.sent_envelope_edit_category_edit_funeral),
                        isActive = false,
                        onClick = {},
                    )
                    SusuFilledButton(
                        color = FilledButtonColor.Orange,
                        style = SmallButtonStyle.height32,
                        text = stringResource(R.string.sent_envelope_edit_category_edit_birthday),
                        isActive = false,
                        onClick = {},
                    )
                    AddConditionButton(
                        onClick = {},
                    )
                }
                EditDetailItem(
                    categoryText = stringResource(R.string.sent_envelope_edit_category_name),
                    categoryTextAlign = Alignment.CenterVertically,
                ) {
                    SusuBasicTextField(
                        text = name,
                        onTextChange = { name = it },
                        placeholder = stringResource(R.string.sent_envelope_edit_category_name_placeholder),
                        placeholderColor = Gray30,
                        textStyle = SusuTheme.typography.title_s,
                        modifier = modifier.fillMaxWidth(),
                    )
                }
                EditDetailItem(
                    categoryText = stringResource(R.string.sent_envelope_edit_category_relationship),
                    categoryTextAlign = Alignment.Top,
                ) {
                    SusuFilledButton(
                        color = FilledButtonColor.Orange,
                        style = SmallButtonStyle.height32,
                        text = stringResource(R.string.sent_envelope_edit_category_relationship_friend),
                        isActive = true,
                        onClick = {},
                    )
                    SusuFilledButton(
                        color = FilledButtonColor.Orange,
                        style = SmallButtonStyle.height32,
                        text = stringResource(R.string.sent_envelope_edit_category_relationship_family),
                        isActive = false,
                        onClick = {},
                    )
                    SusuFilledButton(
                        color = FilledButtonColor.Orange,
                        style = SmallButtonStyle.height32,
                        text = stringResource(R.string.sent_envelope_edit_category_relationship_relatives),
                        isActive = false,
                        onClick = {},
                    )
                    SusuFilledButton(
                        color = FilledButtonColor.Orange,
                        style = SmallButtonStyle.height32,
                        text = stringResource(R.string.sent_envelope_edit_category_relationship_colleague),
                        isActive = false,
                        onClick = {},
                    )
                    AddConditionButton(
                        onClick = {},
                    )
                }
                EditDetailItem(
                    categoryText = stringResource(R.string.sent_envelope_edit_category_date),
                    categoryTextAlign = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "2023년 11월 25일",
                        style = SusuTheme.typography.title_s,
                        color = Gray100,
                        modifier = modifier
                            .fillMaxWidth()
                            .clickable { isSheetOpen = true },
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
                        isActive = true,
                        onClick = {},
                        modifier = modifier.weight(1f),
                    )
                    SusuFilledButton(
                        color = FilledButtonColor.Orange,
                        style = SmallButtonStyle.height32,
                        text = stringResource(R.string.sent_envelope_edit_category_visited_no),
                        isActive = false,
                        onClick = {},
                        modifier = modifier.weight(1f),
                    )
                }
                EditDetailItem(
                    categoryText = stringResource(R.string.sent_envelope_edit_category_present),
                    categoryTextColor = if (present.isNotEmpty()) Gray70 else Gray40,
                    categoryTextAlign = Alignment.CenterVertically,
                ) {
                    SusuBasicTextField(
                        text = present,
                        onTextChange = { present = it },
                        placeholder = stringResource(R.string.sent_envelope_edit_category_present_placeholder),
                        placeholderColor = Gray30,
                        textStyle = SusuTheme.typography.title_s,
                        modifier = modifier.fillMaxWidth(),
                    )
                }
                EditDetailItem(
                    categoryText = stringResource(R.string.sent_envelope_edit_category_phone),
                    categoryTextColor = if (phone.isNotEmpty()) Gray70 else Gray40,
                    categoryTextAlign = Alignment.CenterVertically,
                ) {
                    SusuBasicTextField(
                        text = phone,
                        onTextChange = { phone = it },
                        placeholder = stringResource(R.string.sent_envelope_edit_category_phone_placeholder),
                        placeholderColor = Gray30,
                        textStyle = SusuTheme.typography.title_s,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = modifier.fillMaxWidth(),
                    )
                }
                EditDetailItem(
                    categoryText = stringResource(R.string.sent_envelope_edit_category_memo),
                    categoryTextColor = if (memo.isNotEmpty()) Gray70 else Gray40,
                    categoryTextAlign = Alignment.Top,
                ) {
                    SusuBasicTextField(
                        text = memo,
                        onTextChange = { memo = it },
                        placeholder = stringResource(R.string.sent_envelope_edit_category_memo_placeholder),
                        placeholderColor = Gray30,
                        textStyle = SusuTheme.typography.title_s,
                        maxLines = 2,
                        modifier = modifier.fillMaxWidth(),
                    )
                }
                Spacer(modifier = modifier.size(240.dp))
            }

            SusuFilledButton(
                modifier = modifier
                    .fillMaxWidth()
                    .imePadding(),
                color = FilledButtonColor.Black,
                style = MediumButtonStyle.height60,
                shape = RectangleShape,
                text = stringResource(R.string.sent_envelope_edit_save),
                onClick = onClickSave,
            )
        }

        // DatePickerBottomSheet
        if (isSheetOpen) {
            SusuDatePickerBottomSheet(
                maximumContainerHeight = 346.dp,
                onDismissRequest = { _, _, _ -> isSheetOpen = false },
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
