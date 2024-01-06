package com.susu.core.designsystem.component.searchbar

import androidx.compose.foundation.Image
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.susu.core.designsystem.R
import com.susu.core.designsystem.theme.Gray100
import com.susu.core.designsystem.theme.Gray20
import com.susu.core.designsystem.theme.Gray60
import com.susu.core.designsystem.theme.SusuTheme

@Composable
fun SusuSearchBar(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit = {},
    maxLines: Int = 1,
    minLines: Int = 1,
    textColor: Color = Gray100,
    textStyle: TextStyle = SusuTheme.typography.text_xs,
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
                    .background(Gray20)
                    .padding(
                        horizontal = SusuTheme.spacing.spacing_m,
                        vertical = SusuTheme.spacing.spacing_xxs,
                    )
                    .clip(RoundedCornerShape(4.dp)),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = stringResource(R.string.content_description_search_icon),
                )

                Spacer(modifier = Modifier.size(SusuTheme.spacing.spacing_xxs))

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
            }
        },
    )
}

@Preview
@Composable
fun SusuSearchBarPreview() {
    SusuTheme {
        var text by remember {
            mutableStateOf("")
        }

        SusuSearchBar(
            value = text,
            onValueChange = { text = it },
            placeholder = "찾고 싶은 장부를 검색해보세요",
        )
    }
}
