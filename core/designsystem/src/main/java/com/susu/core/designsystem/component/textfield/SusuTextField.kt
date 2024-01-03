package com.susu.core.designsystem.component.textfield

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.susu.core.designsystem.theme.Gray100
import com.susu.core.designsystem.theme.Gray40
import com.susu.core.designsystem.theme.SusuTheme

@Composable
fun SusuBasicTextField(
    modifier: Modifier = Modifier,
    text: String = "",
    onTextChange: (String) -> Unit = {},
    placeholder: String = "",
    textColor: Color = Gray100,
    placeholderColor: Color = Gray40,
    enabled: Boolean = true,
    textStyle: TextStyle = SusuTheme.typography.title_xl,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    maxLines: Int = 1,
    minLines: Int = 1,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    cursorBrush: Brush = SolidColor(Color.Black),
) {
    BasicTextField(
        modifier = modifier,
        value = text,
        onValueChange = onTextChange,
        enabled = enabled,
        textStyle = textStyle.copy(color = textColor),
        keyboardActions = keyboardActions,
        keyboardOptions = keyboardOptions,
        maxLines = maxLines,
        minLines = minLines,
        visualTransformation = visualTransformation,
        interactionSource = interactionSource,
        cursorBrush = cursorBrush,
    ) { innerTextField ->
        if (text.isEmpty()) {
            Text(
                text = placeholder,
                style = textStyle.copy(color = placeholderColor),
            )
        } else {
            innerTextField()
        }
    }
}

@Composable
fun SusuPriceTextField(
    modifier: Modifier = Modifier,
    text: String = "",
    onTextChange: (String) -> Unit = {},
    placeholder: String = "",
    textColor: Color = Gray100,
    placeholderColor: Color = Gray40,
    enabled: Boolean = true,
    textStyle: TextStyle = SusuTheme.typography.title_xl,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    maxLines: Int = 1,
    minLines: Int = 1,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    cursorBrush: Brush = SolidColor(Color.Black),
) {
    SusuBasicTextField(
        modifier = modifier,
        text = text,
        onTextChange = onTextChange,
        placeholder = placeholder,
        textColor = textColor,
        placeholderColor = placeholderColor,
        enabled = enabled,
        textStyle = textStyle,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        keyboardActions = keyboardActions,
        maxLines = maxLines,
        minLines = minLines,
        interactionSource = interactionSource,
        cursorBrush = cursorBrush,
        visualTransformation = PriceVisualTransformation(),
    )
}

@Composable
@Preview
fun SusuBasicTextFieldPreview() {
    SusuTheme {
        var text by remember { mutableStateOf("") }
        var price by remember { mutableStateOf("") }
        Column {
            SusuBasicTextField(
                text = text,
                onTextChange = { text = it },
                placeholder = "이름을 입력해주세요",
            )
            SusuPriceTextField(
                text = price,
                onTextChange = { price = it },
                placeholder = "금액을 입력해주세요",
            )
        }
    }
}
