package com.susu.feature.envelopeedit.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.susu.core.designsystem.component.button.AddConditionButton
import com.susu.core.designsystem.component.button.FilledButtonColor
import com.susu.core.designsystem.component.button.SmallButtonStyle
import com.susu.core.designsystem.component.button.SusuFilledButton
import com.susu.core.designsystem.component.textfield.SusuBasicTextField
import com.susu.core.designsystem.theme.Gray30
import com.susu.core.designsystem.theme.Gray70
import com.susu.core.designsystem.theme.SusuTheme
import com.susu.feature.sent.R

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun EditDetailItem(
    modifier: Modifier = Modifier,
    categoryText: String,
    categoryStyle: TextStyle = SusuTheme.typography.title_xxs,
    categoryTextColor: Color = Gray70,
    categoryWidth: Dp = 72.dp,
    categoryTextAlign: Alignment.Vertical,
    content: @Composable () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = SusuTheme.spacing.spacing_m),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = categoryText,
            style = categoryStyle,
            color = categoryTextColor,
            modifier = modifier
                .width(categoryWidth)
                .align(categoryTextAlign),
        )
        Spacer(modifier = modifier.size(SusuTheme.spacing.spacing_m))
        FlowRow(
            modifier = modifier.weight(1f),
            horizontalArrangement = Arrangement.spacedBy(SusuTheme.spacing.spacing_xxs),
            verticalArrangement = Arrangement.spacedBy(SusuTheme.spacing.spacing_xxs),
        ) {
            content()
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xffffff)
@Composable
fun DetailItem() {
    var name by remember { mutableStateOf("김철수") }

    SusuTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            // TextField 버전
            EditDetailItem(
                categoryText = "이름",
                categoryTextAlign = Alignment.CenterVertically,
            ) {
                SusuBasicTextField(
                    text = name,
                    onTextChange = { name = it },
                    placeholder = stringResource(R.string.sent_envelope_edit_category_name_placeholder),
                    placeholderColor = Gray30,
                    textStyle = SusuTheme.typography.title_s,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
            // Button 버전
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
        }
    }
}
