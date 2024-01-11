package com.susu.feature.loginsignup.signup.content

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.susu.core.designsystem.component.textfield.SusuUnderlineTextField
import com.susu.core.designsystem.theme.SusuTheme
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
        modifier = modifier.padding(horizontal = 16.dp, vertical = 24.dp),
    ) {
        Text(text = description, style = SusuTheme.typography.title_m)
        Spacer(modifier = Modifier.height(32.dp))
        SusuUnderlineTextField(
            text = text,
            placeholder = "김수수",
            isError = isError,
            lengthLimit = 10,
            description = if (isError && text.isNotEmpty()) "한글과 영문 10자 이내로 입력해주세요" else null,
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
            description = SignUpStep.NAME.description,
        )
    }
}
