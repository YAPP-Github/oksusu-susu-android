package com.susu.core.designsystem.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.susu.core.designsystem.theme.SusuTheme

@Preview
@Composable
fun Sample() {
    SusuTheme {
        Box(modifier = Modifier.padding(SusuTheme.spacing.spacing_l)) {
            Text(
                text = "title_xxxxl",
                style = SusuTheme.typography.title_xxxxl,
                color = SusuTheme.colorScheme.accent,
            )
        }
    }
}
