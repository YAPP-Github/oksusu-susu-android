package com.susu.core.designsystem.component.util

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.susu.core.designsystem.theme.SusuTheme

@Composable
fun LoadingScreen(
    modifier: Modifier = Modifier,
) {
    CircularProgressIndicator(
        modifier = modifier.fillMaxSize(),
        color = SusuTheme.colorScheme.primary,
    )
}
