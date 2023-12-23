package com.susu.feature.community

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.susu.core.designsystem.theme.SusuTheme

@Composable
fun CommunityScreen(
    padding: PaddingValues,
) {
    Text(
        modifier = Modifier.padding(padding),
        text = "투표",
    )
}

@Preview
@Composable
fun CommunityScreenPreview() {
    SusuTheme {
        CommunityScreen(padding = PaddingValues(0.dp))
    }
}
