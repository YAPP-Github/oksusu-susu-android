package com.susu.core.designsystem.component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.susu.core.designsystem.theme.SusuTheme

@Preview
@Composable
fun Sample() {
    SusuTheme {
        Text(
            text = "title_xxxxl",
            style = SusuTheme.typography.title_xxxxl,
            color = SusuTheme.colorScheme.accent,
        )
    }
}
