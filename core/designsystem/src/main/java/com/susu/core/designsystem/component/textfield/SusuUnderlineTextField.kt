package com.susu.core.designsystem.component.textfield

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.susu.core.designsystem.component.button.ClearIconButton
import com.susu.core.designsystem.theme.Gray100
import com.susu.core.designsystem.theme.Gray30
import com.susu.core.designsystem.theme.Gray50
import com.susu.core.designsystem.theme.Red60
import com.susu.core.designsystem.theme.SusuTheme

enum class SusuUnderlineTextFieldColor(
    val textColor: Color,
    val underlineColor: Color,
    val limitationColor: Color,
    val descriptionColor: Color,
) {
    Inactive(
        textColor = Gray30,
        underlineColor = Gray50,
        limitationColor = Gray30,
        descriptionColor = Color.Unspecified,
    ),
    Active(
        textColor = Gray100,
        underlineColor = Gray100,
        limitationColor = Gray30,
        descriptionColor = Color.Unspecified,
    ),
    Error(
        textColor = Gray100,
        underlineColor = Red60,
        limitationColor = Red60,
        descriptionColor = Red60,
    ),
}

data class SusuUnderlineTextFieldTextStyle(
    val contentTextStyle: TextStyle,
    val limitationTextStyle: TextStyle,
    val descriptionTextStyle: TextStyle,
)

object SusuUnderlineTextFieldDefault {
    val textStyle: @Composable () -> SusuUnderlineTextFieldTextStyle = {
        SusuUnderlineTextFieldTextStyle(
            contentTextStyle = SusuTheme.typography.title_l,
            limitationTextStyle = SusuTheme.typography.title_xs,
            descriptionTextStyle = SusuTheme.typography.text_xxs,
        )
    }
}

@Composable
fun SusuUnderlineTextField(
    modifier: Modifier = Modifier,
    text: String = "",
    onTextChange: (String) -> Unit = {},
    placeholder: String = "",
    placeholderColor: Color = Gray30,
    description: String? = null,
    isError: Boolean = false,
    lengthLimit: Int = 20,
    onClickClearIcon: () -> Unit = {},
    textStyle: @Composable () -> SusuUnderlineTextFieldTextStyle = SusuUnderlineTextFieldDefault.textStyle,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    cursorBrush: Brush = SolidColor(Color.Black),
) {
    val textFieldColor = when {
        isError -> SusuUnderlineTextFieldColor.Error
        text.isEmpty() -> SusuUnderlineTextFieldColor.Inactive
        else -> SusuUnderlineTextFieldColor.Active
    }

    with(textStyle()) {
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(SusuTheme.spacing.spacing_s),
        ) {
            Row(
                modifier = Modifier
                    .drawBehind {
                        drawLine(
                            color = textFieldColor.underlineColor,
                            start = Offset(0f, size.height),
                            end = Offset(size.width, size.height),
                            strokeWidth = 1f,
                        )
                    }
                    .padding(SusuTheme.spacing.spacing_xxs),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                SusuBasicTextField(
                    modifier = Modifier.weight(1f),
                    text = text,
                    textColor = textFieldColor.textColor,
                    onTextChange = onTextChange,
                    placeholder = placeholder,
                    placeholderColor = placeholderColor,
                    textStyle = contentTextStyle,
                    keyboardActions = keyboardActions,
                    keyboardOptions = keyboardOptions,
                    visualTransformation = visualTransformation,
                    interactionSource = interactionSource,
                    cursorBrush = cursorBrush,
                )
                if (text.isNotEmpty()) {
                    Box(modifier = Modifier.padding(horizontal = SusuTheme.spacing.spacing_s)) {
                        ClearIconButton(iconSize = 24.dp, onClick = onClickClearIcon)
                    }
                } else {
                    Spacer(modifier = Modifier.width(SusuTheme.spacing.spacing_xxs))
                }
                Text(
                    text = "${text.length}/$lengthLimit",
                    style = limitationTextStyle.copy(color = textFieldColor.limitationColor),
                )
            }
            description?.let { descriptionText ->
                Text(text = descriptionText, style = descriptionTextStyle.copy(color = textFieldColor.descriptionColor))
            }
        }
    }
}

@Preview
@Composable
fun SusuUnderlineTextFieldPreview() {
    SusuTheme {
        var text by remember { mutableStateOf("") }
        Column {
            SusuUnderlineTextField(
                text = text,
                onTextChange = { text = it },
                placeholder = "김수수",
            )
            SusuUnderlineTextField(
                text = text,
                onTextChange = { text = it },
                placeholder = "김수수",
                isError = true,
                description = "에러 메세지를 입력하세요",
            )
        }
    }
}
