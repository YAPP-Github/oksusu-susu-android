package com.susu.core.designsystem.component.appbar.icon

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.susu.core.designsystem.R
import com.susu.core.designsystem.theme.SusuTheme
import com.susu.core.ui.extension.susuClickable

@Composable
fun RegisterText(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    Text(
        modifier = modifier.susuClickable(
            rippleEnabled = false,
            onClick = onClick,
        ),
        text = stringResource(com.susu.core.ui.R.string.word_register),
        style = SusuTheme.typography.title_xxs,
    )
}

@Preview
@Composable
fun RegisterTextPreview() {
    SusuTheme {
        RegisterText()
    }
}
