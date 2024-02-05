package com.susu.feature.envelopedetail.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.susu.core.designsystem.theme.Gray100
import com.susu.core.designsystem.theme.Gray60
import com.susu.core.designsystem.theme.SusuTheme

@Composable
fun DetailItem(
    categoryText: String,
    contentText: String,
    modifier: Modifier = Modifier,
    isEmptyContent: Boolean = false,
    categoryStyle: TextStyle = SusuTheme.typography.title_xxs,
    categoryTextColor: Color = Gray60,
    categoryWidth: Dp = 72.dp,
    contentStyle: TextStyle = SusuTheme.typography.title_s,
    contentColor: Color = Gray100,
    padding: PaddingValues = PaddingValues(vertical = SusuTheme.spacing.spacing_m),
) {
    if (!isEmptyContent) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(padding),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = categoryText,
                style = categoryStyle,
                color = categoryTextColor,
                modifier = modifier
                    .width(categoryWidth)
                    .align(Alignment.Top),
            )
            Spacer(modifier = modifier.size(SusuTheme.spacing.spacing_m))
            Text(
                text = contentText,
                style = contentStyle,
                color = contentColor,
                modifier = modifier.weight(1f),
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xffffff)
@Composable
fun DetailItem() {
    SusuTheme {
        DetailItem(
            categoryText = "경조사",
            contentText = "결혼식",
            isEmptyContent = false,
        )
    }
}
