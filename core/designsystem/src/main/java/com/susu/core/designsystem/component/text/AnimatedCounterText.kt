package com.susu.core.designsystem.component.text

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import com.susu.core.designsystem.theme.Gray100
import com.susu.core.designsystem.theme.SusuTheme
import java.text.DecimalFormat

private val upward = slideInVertically { it } togetherWith slideOutVertically { -it }
private val downward = slideInVertically { -it } togetherWith slideOutVertically { it }

@Composable
fun AnimatedCounterText(
    number: Long,
    modifier: Modifier = Modifier,
    prefix: String? = null,
    postfix: String? = null,
    style: TextStyle = SusuTheme.typography.title_xs,
    color: Color = Gray100,
) {
    val moneyFormat = remember { DecimalFormat("#,###") }
    val currentString = moneyFormat.format(number)

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.End,
    ) {
        prefix?.let {
            Text(
                text = it,
                style = style,
                color = color,
            )
        }
        for (i in currentString.indices) {
            val char = currentString[i]

            AnimatedContent(
                targetState = char,
                transitionSpec = {
                    when {
                        initialState.isDigit() && targetState.isDigit() &&
                            initialState.digitToInt() < targetState.digitToInt() -> upward

                        initialState.isDigit() && targetState.isDigit() &&
                            initialState.digitToInt() > targetState.digitToInt() -> downward

                        else -> upward
                    }
                },
                label = "animatedCounterChar$i",
            ) {
                Text(
                    text = it.toString(),
                    style = style,
                    color = color,
                )
            }
        }
        postfix?.let {
            Text(
                text = it,
                style = style,
                color = color,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AnimatedCounterTextPreview() {
    SusuTheme {
        var number by remember { mutableStateOf(10000L) }
        Column {
            Button(onClick = { number += 6000 }) {
                Text(text = "증가")
            }
            AnimatedCounterText(number = number)
        }
    }
}
