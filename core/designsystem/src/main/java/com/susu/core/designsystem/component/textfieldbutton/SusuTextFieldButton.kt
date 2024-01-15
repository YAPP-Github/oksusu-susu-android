package com.susu.core.designsystem.component.textfieldbutton

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.susu.core.designsystem.R
import com.susu.core.designsystem.component.button.ClearIconButton
import com.susu.core.designsystem.component.button.FilledButtonColor
import com.susu.core.designsystem.component.button.LargeButtonStyle
import com.susu.core.designsystem.component.button.SusuFilledButton
import com.susu.core.designsystem.component.textfieldbutton.style.InnerButtonStyle
import com.susu.core.designsystem.component.textfieldbutton.style.LargeTextFieldButtonStyle
import com.susu.core.designsystem.component.textfieldbutton.style.SmallTextFieldButtonStyle
import com.susu.core.designsystem.component.textfieldbutton.style.TextFieldButtonStyle
import com.susu.core.designsystem.theme.SusuTheme
import com.susu.core.ui.extension.disabledHorizontalPointerInputScroll
import com.susu.core.ui.extension.susuClickable

@Composable
fun SusuTextFieldFillMaxButton(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(4.dp),
    text: String = "",
    onTextChange: (String) -> Unit = {},
    placeholder: String = "",
    maxLines: Int = 1,
    minLines: Int = 1,
    overflow: TextOverflow = TextOverflow.Clip,
    style: @Composable () -> TextFieldButtonStyle,
    color: TextFieldButtonColor = TextFieldButtonColor.Black,
    isSaved: Boolean = false,
    isFocused: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    showCloseIcon: Boolean = true,
    showClearIcon: Boolean = true,
    onClickClearIcon: () -> Unit = {},
    onClickCloseIcon: () -> Unit = {},
    onClickFilledButton: () -> Unit = {},
    onClickButton: (isFocused: Boolean) -> Unit = {},
) {
    val (backgroundColor, textColor) = with(color) {
        when {
            isFocused.not() -> (unFocusedBackgroundColor to unFocusedTextColor)
            isSaved -> (savedBackgroundColor to savedTextColor)
            else -> (editBackgroundColor to editTextColor)
        }
    }

    with(style()) {
        BasicTextField(
            modifier = modifier
                .fillMaxWidth()
                .susuClickable { onClickButton(isFocused) },
            value = text,
            onValueChange = onTextChange,
            enabled = isSaved.not() && isFocused,
            singleLine = maxLines == 1,
            maxLines = if (minLines > maxLines) minLines else maxLines,
            minLines = minLines,
            textStyle = textStyle.copy(textAlign = TextAlign.Center, color = textColor),
            interactionSource = interactionSource,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier
                        .clip(shape)
                        .background(backgroundColor)
                        .padding(paddingValues),
                    horizontalArrangement = Arrangement.spacedBy(iconSpacing),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Box(
                        modifier = Modifier.weight(1f),
                        contentAlignment = Alignment.Center,
                    ) {
                        if (isSaved.not()) {
                            innerTextField()
                        }

                        /**
                         * Problem : innerTextField 수정 중에 saved가 되면 underline이 생기는 현상.
                         * Solution : saved 상태에서는 Text 컴포저블 함수가 보이게 함.
                         */
                        if (isSaved) {
                            Text(
                                text = text,
                                color = textColor,
                                style = textStyle,
                                textAlign = TextAlign.Center,
                                overflow = overflow,
                                maxLines = maxLines,
                                minLines = minLines,
                            )
                        }

                        if (text.isEmpty()) {
                            Text(
                                text = placeholder,
                                color = color.placeholderColor,
                                style = textStyle,
                                textAlign = TextAlign.Center,
                                overflow = overflow,
                                maxLines = maxLines,
                                minLines = minLines,
                            )
                        }
                    }

                    InnerButtons(
                        showCloseIcon = showCloseIcon,
                        showClearIcon = showClearIcon,
                        isSaved = isSaved,
                        isActive = text.isNotEmpty(),
                        isFocused = isFocused,
                        color = color.buttonColor,
                        buttonStyle = innerButtonStyle,
                        clearIconSize = clearIconSize,
                        closeIconSize = closeIconSize,
                        onClickClearIcon = onClickClearIcon,
                        onClickCloseIcon = onClickCloseIcon,
                        onClickFilledButton = onClickFilledButton,
                    )
                }
            },
        )
    }
}

@Composable
fun SusuTextFieldWrapContentButton(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(4.dp),
    text: String = "",
    onTextChange: (String) -> Unit = {},
    placeholder: String = "",
    maxLines: Int = 1,
    minLines: Int = 1,
    overflow: TextOverflow = TextOverflow.Clip,
    style: @Composable () -> TextFieldButtonStyle,
    color: TextFieldButtonColor = TextFieldButtonColor.Black,
    isSaved: Boolean = false,
    isFocused: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    showCloseIcon: Boolean = true,
    showClearIcon: Boolean = true,
    onClickClearIcon: () -> Unit = {},
    onClickCloseIcon: () -> Unit = {},
    onClickFilledButton: () -> Unit = {},
    onClickButton: () -> Unit = {},
    focusRequester: FocusRequester = remember { FocusRequester() },
) {
    val (backgroundColor, textColor) = with(color) {
        when {
            isFocused.not() -> (unFocusedBackgroundColor to unFocusedTextColor)
            isSaved -> (savedBackgroundColor to savedTextColor)
            else -> (editBackgroundColor to editTextColor)
        }
    }

    with(style()) {
        Row(
            modifier = modifier
                .clip(shape)
                .background(backgroundColor)
                .susuClickable (onClick = onClickButton)
                .padding(paddingValues),
            horizontalArrangement = Arrangement.spacedBy(iconSpacing),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                contentAlignment = Alignment.Center,
            ) {
                BasicTextField(
                    modifier = Modifier
                        .disabledHorizontalPointerInputScroll()
                        .widthIn(min = 2.dp)
                        /**
                         * BasicTextField의 기본 width를 없애기 위해 IntrinsicSize.Min을 사용함.
                         * see -> https://stackoverflow.com/questions/67719981/resizeable-basictextfield-in-jetpack-compose
                         */
                        .width(IntrinsicSize.Min)
                        .susuClickable(rippleEnabled = false, runIf = isFocused.not(), onClick = onClickButton)
                        .focusRequester(focusRequester),
                    value = text,
                    enabled = isSaved.not() && isFocused,
                    onValueChange = onTextChange,
                    singleLine = maxLines == 1,
                    maxLines = if (minLines > maxLines) minLines else maxLines,
                    minLines = minLines,
                    textStyle = textStyle
                        .copy(color = Color.Transparent),
                    interactionSource = interactionSource,
                    keyboardOptions = keyboardOptions,
                    keyboardActions = keyboardActions,
                    decorationBox = { innerTextField ->
                        /**
                         * Problem : 만약 커서가 맨 끝으로 가게 된다면 innerTextField의 텍스트가 짤리는 현상 발생.
                         * Why : IntrinsicSize.Min을 사용하게 되면 innerTextField의 width는 텍스트의 너비 만큼만 설정이 되었기 때문. (커서의 너비는 포함되지 않음.)
                         * Solution :
                         * innerTextField의 color를 Transparent로 설정해 텍스트가 짤리는 현상을 안보이게 함.
                         * 눈에 보이는 텍스트는 Text 컴포저블 함수로 보이게 함.
                         */
                        Text(
                            text = text,
                            color = textColor,
                            style = textStyle,
                            overflow = overflow,
                            maxLines = maxLines,
                            minLines = minLines,
                        )
                        innerTextField()
                    },
                )

                if (text.isEmpty()) {
                    Text(
                        text = placeholder,
                        color = color.placeholderColor,
                        style = textStyle,
                        textAlign = TextAlign.Center,
                        overflow = overflow,
                        maxLines = maxLines,
                        minLines = minLines,
                    )
                }
            }

            InnerButtons(
                showCloseIcon = showCloseIcon,
                showClearIcon = showClearIcon,
                isSaved = isSaved,
                isActive = text.isNotEmpty(),
                isFocused = isFocused,
                color = color.buttonColor,
                buttonStyle = innerButtonStyle,
                clearIconSize = clearIconSize,
                closeIconSize = closeIconSize,
                onClickClearIcon = onClickClearIcon,
                onClickCloseIcon = onClickCloseIcon,
                onClickFilledButton = onClickFilledButton,
            )
        }
    }
}

@Composable
private fun InnerButtons(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(4.dp),
    isSaved: Boolean,
    isActive: Boolean,
    isFocused: Boolean,
    showCloseIcon: Boolean = true,
    showClearIcon: Boolean = true,
    color: TextButtonInnerButtonColor,
    clearIconSize: Dp = 0.dp,
    closeIconSize: Dp = 0.dp,
    buttonStyle: @Composable () -> InnerButtonStyle,
    onClickClearIcon: () -> Unit = {},
    onClickCloseIcon: () -> Unit = {},
    onClickFilledButton: () -> Unit = {},
) {
    val (innerButtonTextColor, innerButtonBackgroundColor) = with(color) {
        when {
            isFocused.not() -> (unFocusedContentColor to unFocusedBackgroundColor)
            isActive || isSaved -> (activeContentColor to activeBackgroundColor)
            else -> (inactiveContentColor to inactiveBackgroundColor)
        }
    }

    if (isSaved.not()) {
        Box(modifier = Modifier.size(clearIconSize)) {
            if (showClearIcon) {
                ClearIconButton(
                    iconSize = clearIconSize,
                    onClick = onClickClearIcon,
                )
            }
        }
    }

    if (isSaved && showCloseIcon.not()) {
        Box(modifier = Modifier.size(closeIconSize))
    }

    with(buttonStyle()) {
        Box(
            modifier = modifier
                .clip(shape)
                .background(innerButtonBackgroundColor)
                .susuClickable(
                    runIf = isActive || isSaved,
                    rippleColor = color.rippleColor,
                    onClick = onClickFilledButton,
                )
                .padding(paddingValues),
        ) {
            Text(
                text = if (isSaved) stringResource(com.susu.core.ui.R.string.word_edit) else stringResource(com.susu.core.ui.R.string.word_save),
                style = textStyle,
                color = innerButtonTextColor,
            )
        }
    }

    if (isSaved && showCloseIcon) {
        Image(
            modifier = Modifier
                .clip(CircleShape)
                .size(closeIconSize)
                .susuClickable(onClick = onClickCloseIcon),
            painter = painterResource(id = com.susu.core.ui.R.drawable.ic_close),
            contentDescription = "",
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0x000000)
@Composable
fun TextFieldButtonPreview() {
    SusuTheme {
        var text by remember {
            mutableStateOf("Button")
        }

        var isSaved by remember {
            mutableStateOf(false)
        }

        var isFocused by remember {
            mutableStateOf(true)
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            Text(text = "텍스트 길이에 딱 맞는 너비 (wrap)")
            SusuTextFieldWrapContentButton(
                text = text,
                onTextChange = { text = it },
                placeholder = "",
                maxLines = 1,
                minLines = 1,
                showClearIcon = true,
                isFocused = isFocused,
                onClickButton = { isFocused = !isFocused },
                style = LargeTextFieldButtonStyle.height46,
                onClickFilledButton = { isSaved = isSaved.not() },
                onClickClearIcon = { text = "" },
                isSaved = isSaved,
            )

            HorizontalDivider()

            Text(text = "최대 너비 (fillMaxWidth)")
            SusuTextFieldFillMaxButton(
                text = text,
                onTextChange = { text = it },
                placeholder = "Button",
                maxLines = 1,
                minLines = 1,
                isFocused = !isFocused,
                onClickButton = { isFocused = !isFocused },
                style = LargeTextFieldButtonStyle.height46,
                onClickFilledButton = { isSaved = isSaved.not() },
                onClickClearIcon = { text = "" },
                isSaved = isSaved,
            )

            HorizontalDivider()

            Text(text = "텍스트가 없는 경우 : placeHolder 길이에 딱 맞는 너비\n텍스트가 있는 경우 : 텍스트가 차지하는 너비")
            SusuTextFieldWrapContentButton(
                text = text,
                onTextChange = { text = it },
                placeholder = "Button",
                maxLines = 1,
                minLines = 1,
                isFocused = !isFocused,
                onClickButton = { isFocused = !isFocused },
                showClearIcon = false,
                showCloseIcon = false,
                color = TextFieldButtonColor.Orange,
                style = LargeTextFieldButtonStyle.height46,
                onClickFilledButton = { isSaved = isSaved.not() },
                onClickClearIcon = { text = "" },
                isSaved = isSaved,
            )

            HorizontalDivider()

            Text(text = "고정된 너비 : 200dp")
            SusuTextFieldFillMaxButton(
                modifier = Modifier.width(200.dp),
                text = text,
                overflow = TextOverflow.Ellipsis,
                onTextChange = { text = it },
                placeholder = "Button",
                isFocused = !isFocused,
                onClickButton = { isFocused = !isFocused },
                maxLines = 1,
                minLines = 1,
                color = TextFieldButtonColor.Orange,
                style = SmallTextFieldButtonStyle.height32,
                onClickFilledButton = { isSaved = isSaved.not() },
                onClickClearIcon = { text = "" },
                isSaved = isSaved,
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0x000000)
@Composable
fun TextFieldButtonFocusedPreview() {
    SusuTheme {
        var text by remember {
            mutableStateOf("Button")
        }

        var isSaved by remember {
            mutableStateOf(false)
        }

        var isFocusedTextFieldButton by remember {
            mutableStateOf(true)
        }

        var isFocusedButton1 by remember {
            mutableStateOf(false)
        }

        var isFocusedButton2 by remember {
            mutableStateOf(false)
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            Text(text = "텍스트 길이에 딱 맞는 너비 (wrap)")
            SusuTextFieldWrapContentButton(
                color = TextFieldButtonColor.Orange,
                text = text,
                onTextChange = { text = it },
                placeholder = "",
                maxLines = 1,
                minLines = 1,
                showClearIcon = true,
                isFocused = isFocusedTextFieldButton,
                onClickButton = {
                    isFocusedTextFieldButton = true
                    isFocusedButton1 = false
                    isFocusedButton2 = false
                },
                style = LargeTextFieldButtonStyle.height46,
                onClickFilledButton = { isSaved = isSaved.not() },
                onClickClearIcon = { text = "" },
                isSaved = isSaved,
            )

            SusuFilledButton(
                color = FilledButtonColor.Orange,
                style = LargeButtonStyle.height46,
                text = "테스트1",
                isActive = isFocusedButton1,
                onClick = {
                    isFocusedTextFieldButton = false
                    isFocusedButton1 = true
                    isFocusedButton2 = false
                },
            )

            SusuFilledButton(
                color = FilledButtonColor.Orange,
                style = LargeButtonStyle.height46,
                text = "테스트2",
                isActive = isFocusedButton2,
                onClick = {
                    isFocusedTextFieldButton = false
                    isFocusedButton1 = false
                    isFocusedButton2 = true
                },
            )
        }
    }
}
