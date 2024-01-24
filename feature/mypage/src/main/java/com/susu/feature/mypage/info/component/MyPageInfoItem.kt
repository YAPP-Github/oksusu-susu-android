package com.susu.feature.mypage.info.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.susu.core.designsystem.theme.Gray60
import com.susu.core.designsystem.theme.Red60
import com.susu.core.designsystem.theme.SusuTheme

@Composable
fun MyPageInfoItem(
    modifier: Modifier = Modifier,
    title: String = "",
    isWrong: Boolean = false,
    content: @Composable () -> Unit,
) {
    Row(
        modifier = modifier.fillMaxWidth().padding(SusuTheme.spacing.spacing_m),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row {
            Text(text = title, style = SusuTheme.typography.title_xxs, color = Gray60)
            if (isWrong) {
                Spacer(modifier = Modifier.width(SusuTheme.spacing.spacing_xxxxs))
                WrongDot()
            }
        }

        content()
    }
}

@Composable
fun WrongDot(
    modifier: Modifier = Modifier,
    dotRadius: Dp = 2.dp,
    color: Color = Red60,
) {
    Spacer(
        modifier = modifier.width(4.dp).wrapContentHeight()
            .drawBehind {
                drawCircle(
                    color = color,
                    radius = dotRadius.toPx(),
                    center = Offset(0f, 8.dp.toPx())
                )
            },
    )
}

@Preview
@Composable
fun MyPageInfoItemPreview() {
    SusuTheme {
        MyPageInfoItem(
            title = "이름",
            isWrong = true,
        ) {
        }
    }
}
