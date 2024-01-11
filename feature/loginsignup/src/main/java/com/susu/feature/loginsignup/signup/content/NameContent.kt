package com.susu.feature.loginsignup.signup.content

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.susu.core.designsystem.component.textfield.SusuUnderlineTextField
import com.susu.core.designsystem.theme.SusuTheme
import com.susu.feature.loginsignup.R
import com.susu.feature.loginsignup.signup.SignUpStep

@Composable
fun NameContent(
    modifier: Modifier = Modifier,
    description: String = "",
    text: String = "",
    isError: Boolean = false,
    onTextChange: (String) -> Unit = {},
    onClickClearIcon: () -> Unit = {},
) {
    Column(
        modifier = modifier.padding(horizontal = SusuTheme.spacing.spacing_m, vertical = SusuTheme.spacing.spacing_xl),
    ) {
        Text(text = description, style = SusuTheme.typography.title_m)
        Spacer(modifier = Modifier.height(SusuTheme.spacing.spacing_xxl))
        SusuUnderlineTextField(
            text = text,
            placeholder = stringResource(R.string.signup_name_sample),
            isError = isError,
            lengthLimit = 10,
            description = if (isError && text.isNotEmpty()) stringResource(R.string.signup_name_limitation) else null,
            onTextChange = onTextChange,
            onClickClearIcon = onClickClearIcon,
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun NameContentPreview() {
    SusuTheme {
        NameContent(
            modifier = Modifier.fillMaxSize(),
        )
    }
}
