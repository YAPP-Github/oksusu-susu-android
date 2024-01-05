package com.susu.feature.received.received.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.susu.core.designsystem.theme.Gray40
import com.susu.core.designsystem.theme.SusuTheme
import com.susu.core.ui.extension.susuClickable
import com.susu.feature.received.R

@Composable
fun LedgerAddCard(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    val (strokeWidthDp, dashWidthDp, gapWidthDp) = listOf(1.dp, 4.dp, 4.dp)

    val (strokeWidthPx, dashWidthPx, gapWidthPx) = with(LocalDensity.current) {
        listOf(
            strokeWidthDp.toPx(),
            dashWidthDp.toPx(),
            gapWidthDp.toPx(),
        )
    }

    Box(
        modifier = modifier
            .aspectRatio(1f)
            .susuClickable(onClick = onClick),
    ) {
        val stroke = Stroke(
            width = strokeWidthPx,
            pathEffect = PathEffect.dashPathEffect(floatArrayOf(dashWidthPx, gapWidthPx), 0f),
        )
        Canvas(
            Modifier
                .fillMaxSize()
                .padding(strokeWidthDp / 2),
        ) {
            drawRoundRect(color = Gray40, style = stroke)
        }

        Icon(
            modifier = Modifier.align(Alignment.Center),
            painter = painterResource(id = R.drawable.ic_ledger_add),
            contentDescription = "장부 추가 아이콘",
            tint = Gray40,
        )
    }
}

@Preview
@Composable
fun LedgerAddCardPreview() {
    SusuTheme {
        LedgerAddCard()
    }
}
