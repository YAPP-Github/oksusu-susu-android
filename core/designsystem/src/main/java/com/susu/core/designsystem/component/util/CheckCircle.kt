package com.susu.core.designsystem.component.util

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.susu.core.designsystem.R
import com.susu.core.designsystem.theme.Gray100
import com.susu.core.designsystem.theme.Gray40
import com.susu.core.designsystem.theme.SusuTheme
import com.susu.core.ui.extension.susuClickable

@Composable
fun CheckCircle(
    modifier: Modifier = Modifier,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit = {},
) {
    Box(
        modifier = modifier
            .size(20.dp)
            .drawBehind {
                if (isChecked.not()) {
                    drawCircle(
                        color = Gray40,
                        radius = 8.dp.toPx(),
                        style = Stroke(width = 1.dp.toPx()),
                    )
                }
            }
            .susuClickable(
                rippleEnabled = false,
                onClick = { onCheckedChange(isChecked.not()) },
            ),
    ) {
        if (isChecked) {
            Icon(
                painter = painterResource(id = R.drawable.ic_check_circle_background),
                contentDescription = null,
                tint = Gray100,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CheckCirclePreview() {
    SusuTheme {
        Row {
            CheckCircle(isChecked = true)
            CheckCircle(isChecked = false)
        }
    }
}
