package com.susu.feature.received.ledgerfilter.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.susu.core.designsystem.theme.Gray100
import com.susu.core.designsystem.theme.Gray15
import com.susu.core.designsystem.theme.Gray40
import com.susu.core.designsystem.theme.SusuTheme
import com.susu.core.ui.extension.susuClickable
import com.susu.core.ui.util.currentDate
import com.susu.core.ui.util.to_yyyy_dot_MM_dot_dd
import java.time.LocalDateTime

@Composable
fun DateText(
    date: LocalDateTime? = null,
    onClick: () -> Unit = {},
) {
    Text(
        modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .background(Gray15)
            .padding(
                horizontal = SusuTheme.spacing.spacing_m,
                vertical = SusuTheme.spacing.spacing_xxxxs,
            )
            .susuClickable(rippleEnabled = false, onClick = onClick),
        text = date?.to_yyyy_dot_MM_dot_dd() ?: currentDate.to_yyyy_dot_MM_dot_dd(),
        style = SusuTheme.typography.title_xs,
        color = if (date == null) Gray40 else Gray100,
    )
}

@Preview
@Composable
fun DateTextPreview() {
    SusuTheme {
        DateText()
    }
}
