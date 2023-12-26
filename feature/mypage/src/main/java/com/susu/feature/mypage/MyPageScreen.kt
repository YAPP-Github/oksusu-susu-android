package com.susu.feature.mypage

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.susu.core.designsystem.theme.SusuTheme

@Composable
fun MyPageScreen(
    padding: PaddingValues,
) {
    Text(
        modifier = Modifier.padding(padding),
        text = "마이 페이지",
    )
}

@Preview
@Composable
fun MyPageScreenPreview() {
    SusuTheme {
        MyPageScreen(padding = PaddingValues(0.dp))
    }
}
