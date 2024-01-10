package com.susu.feature.received.ledgeredit.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.susu.core.designsystem.theme.Gray60
import com.susu.core.designsystem.theme.SusuTheme

@Composable
fun LedgerEditContainer(
    verticalAlignment: Alignment.Vertical,
    name: String,
    content: @Composable () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = SusuTheme.spacing.spacing_m),
        horizontalArrangement = Arrangement.spacedBy(SusuTheme.spacing.spacing_xxs),
        verticalAlignment = verticalAlignment,
    ) {
        Text(
            modifier = Modifier.width(72.dp),
            text = name,
            style = SusuTheme.typography.title_xxs,
            color = Gray60,
        )

        content()
    }
}

@Preview
@Composable
fun LedgerEditContainerPreview() {
    SusuTheme {
        LedgerEditContainer(
            name = "경조사명", verticalAlignment = Alignment.CenterVertically,
            content = { Text(text = "고모부 장례식", style = SusuTheme.typography.title_m) },
        )
    }
}
