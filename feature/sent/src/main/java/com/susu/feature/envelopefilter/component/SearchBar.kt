package com.susu.feature.envelopefilter.component

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.susu.core.designsystem.R
import com.susu.core.designsystem.component.button.ClearIconButton
import com.susu.core.designsystem.theme.Gray100
import com.susu.core.designsystem.theme.Gray20
import com.susu.core.designsystem.theme.Gray60
import com.susu.core.designsystem.theme.SusuTheme

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit = {},
    onClickClearIcon: () -> Unit = {},
    maxLines: Int = 1,
    minLines: Int = 1,
    textColor: Color = Gray100,
    textStyle: TextStyle = SusuTheme.typography.text_xxs,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    placeholder: String,
    placeholderColor: Color = Gray60,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    cursorBrush: Brush = SolidColor(Color.Black),
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        textStyle = textStyle.copy(color = textColor),
        singleLine = maxLines == 1,
        maxLines = if (minLines > maxLines) minLines else maxLines,
        minLines = minLines,
        interactionSource = interactionSource,
        cursorBrush = cursorBrush,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        decorationBox = { innerText ->
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .background(Gray20)
                    .padding(
                        horizontal = SusuTheme.spacing.spacing_s,
                        vertical = SusuTheme.spacing.spacing_xxxs,
                    ),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    modifier = Modifier.size(20.dp),
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = null,
                    tint = Gray60,
                )

                Spacer(modifier = Modifier.size(SusuTheme.spacing.spacing_xxxxs))

                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.CenterStart,
                ) {
                    innerText()
                    if (value.isEmpty()) {
                        Text(
                            text = placeholder,
                            color = placeholderColor,
                            style = textStyle,
                        )
                    }
                }

                if (value.isNotEmpty()) {
                    ClearIconButton(
                        iconSize = 20.dp,
                        onClick = onClickClearIcon,
                    )
                }
            }
        },
    )
}

@Preview
@Composable
fun SearchBarPreview() {
    SusuTheme {
        var text by remember {
            mutableStateOf("보낸 사람 검색")
        }

        SearchBar(
            value = text,
            onValueChange = { text = it },
            placeholder = "찾고 싶은 장부를 검색해보세요",
        )
    }
}
