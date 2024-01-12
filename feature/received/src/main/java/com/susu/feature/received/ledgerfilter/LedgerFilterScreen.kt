package com.susu.feature.received.ledgerfilter

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.susu.core.designsystem.theme.SusuTheme

@Composable
fun LedgerFilterRoute(
    @Suppress("unused")
    popBackStack: () -> Unit,
) {
    LedgerFilterScreen()
}

@Composable
fun LedgerFilterScreen() {
    Box(
        modifier = Modifier
            .background(SusuTheme.colorScheme.background15)
            .fillMaxSize(),
    ) {
        Text(text = "장부 필터")
    }
}

@Preview
@Composable
fun LedgerFilterScreenPreview() {
    SusuTheme {
        LedgerFilterScreen()
    }
}
