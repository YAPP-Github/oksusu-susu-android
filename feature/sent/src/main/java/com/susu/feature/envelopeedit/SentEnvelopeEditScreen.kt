package com.susu.feature.envelopeedit

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
import com.susu.core.designsystem.theme.SusuTheme
import com.susu.feature.envelopeedit.component.EditDetailItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SentEnvelopeEditScreen(
    modifier: Modifier = Modifier,
    onClickBackIcon: () -> Unit = {},
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
            .background(SusuTheme.colorScheme.background10)
            .navigationBarsPadding(),
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
                    .fillMaxWidth()
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
                    categoryText = "경조사",
                    categoryTextAlign = Alignment.Top,
                ) {
                    SusuFilledButton(
                        color = FilledButtonColor.Orange,
                        style = SmallButtonStyle.height32,
                        text = "결혼식",
                        isActive = true,
                        onClick = {},
                    )
                    SusuFilledButton(
                        color = FilledButtonColor.Orange,
                        style = SmallButtonStyle.height32,
                        text = "돌잔치",
                        isActive = false,
                        onClick = {},
                    )
                    SusuFilledButton(
                        color = FilledButtonColor.Orange,
                        style = SmallButtonStyle.height32,
                        text = "장례식",
                        isActive = false,
                        onClick = {},
                    )
                    SusuFilledButton(
                        color = FilledButtonColor.Orange,
                        style = SmallButtonStyle.height32,
                        text = "생일 기념일",
                        isActive = false,
                        onClick = {},
                    )
                    AddConditionButton(
                        onClick = {},
                    )
                }
                EditDetailItem(
                    categoryText = "이름",
                    categoryTextAlign = Alignment.CenterVertically,
                ) {
                    SusuBasicTextField(
                        text = name,
                        onTextChange = { name = it },
                        placeholder = "김철수",
                        placeholderColor = Gray30,
                        textStyle = SusuTheme.typography.title_m,
                        modifier = modifier.fillMaxWidth(),
                    )
                }
                EditDetailItem(
                    categoryText = "나와의 관계",
                    categoryTextAlign = Alignment.Top,
                ) {
                    SusuFilledButton(
                        color = FilledButtonColor.Orange,
                        style = SmallButtonStyle.height32,
                        text = "친구",
                        isActive = true,
                        onClick = {},
                    )
                    SusuFilledButton(
                        color = FilledButtonColor.Orange,
                        style = SmallButtonStyle.height32,
                        text = "가족",
                        isActive = false,
                        onClick = {},
                    )
                    SusuFilledButton(
                        color = FilledButtonColor.Orange,
                        style = SmallButtonStyle.height32,
                        text = "친척",
                        isActive = false,
                        onClick = {},
                    )
                    SusuFilledButton(
                        color = FilledButtonColor.Orange,
                        style = SmallButtonStyle.height32,
                        text = "동료",
                        isActive = false,
                        onClick = {},
                    )
                    AddConditionButton(
                        onClick = {},
                    )
                }
                EditDetailItem(
                    categoryText = "날짜",
                    categoryTextAlign = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "2023년 11월 25일",
                        style = SusuTheme.typography.title_m,
                        color = Gray100,
                        modifier = modifier
                            .fillMaxWidth()
                            .clickable { isSheetOpen = true },
                    )
                }
                EditDetailItem(
                    categoryText = "방문 여부",
                    categoryTextAlign = Alignment.Top,
                ) {
                    SusuFilledButton(
                        color = FilledButtonColor.Orange,
                        style = SmallButtonStyle.height32,
                        text = "예",
                        isActive = true,
                        onClick = {},
                        modifier = modifier.weight(1f),
                    )
                    SusuFilledButton(
                        color = FilledButtonColor.Orange,
                        style = SmallButtonStyle.height32,
                        text = "아니요",
                        isActive = false,
                        onClick = {},
                        modifier = modifier.weight(1f),
                    )
                }
                EditDetailItem(
                    categoryText = "선물",
                    categoryTextColor = if (present.isNotEmpty()) Gray100 else Gray40,
                    categoryTextAlign = Alignment.CenterVertically,
                ) {
                    SusuBasicTextField(
                        text = present,
                        onTextChange = { present = it },
                        placeholder = "한끼 식사",
                        placeholderColor = Gray30,
                        textStyle = SusuTheme.typography.title_m,
                        modifier = modifier.fillMaxWidth(),
                    )
                }
                EditDetailItem(
                    categoryText = "연락처",
                    categoryTextColor = if (phone.isNotEmpty()) Gray100 else Gray40,
                    categoryTextAlign = Alignment.CenterVertically,
                ) {
                    SusuBasicTextField(
                        text = phone,
                        onTextChange = { phone = it },
                        placeholder = "01012345678",
                        placeholderColor = Gray30,
                        textStyle = SusuTheme.typography.title_m,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = modifier.fillMaxWidth(),
                    )
                }
                EditDetailItem(
                    categoryText = "메모",
                    categoryTextColor = if (memo.isNotEmpty()) Gray100 else Gray40,
                    categoryTextAlign = Alignment.Top,
                ) {
                    SusuBasicTextField(
                        text = memo,
                        onTextChange = { memo = it },
                        placeholder = "입력해주세요",
                        placeholderColor = Gray30,
                        textStyle = SusuTheme.typography.title_m,
                        minLines = 2,
                        maxLines = 2,
                        modifier = modifier.fillMaxWidth(),
                    )
                }
                Spacer(modifier = modifier.size(400.dp))
            }
        }
        SusuFilledButton(
            modifier = modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .imePadding(),
            color = FilledButtonColor.Black,
            style = MediumButtonStyle.height60,
            shape = RectangleShape,
            text = "저장",
            onClick = {},
        )

        // DatePickerBottomSheet
        if (isSheetOpen) {
            SusuDatePickerBottomSheet(
                maximumContainerHeight = 346.dp,
                onDismissRequest = { _, _, _ -> isSheetOpen = false }
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
