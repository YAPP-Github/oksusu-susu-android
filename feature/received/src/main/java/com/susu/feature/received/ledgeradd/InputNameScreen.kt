package com.susu.feature.received.ledgeradd

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.susu.core.designsystem.component.textfield.SusuBasicTextField
import com.susu.core.designsystem.theme.SusuTheme

@Composable
fun InputNameScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = SusuTheme.spacing.spacing_xl,
                start = SusuTheme.spacing.spacing_m,
                end = SusuTheme.spacing.spacing_m,
            ),
    ) {
        Text(
            text = "경조사명을 알려주세요",
            style = SusuTheme.typography.title_m,
        )

        Spacer(modifier = Modifier.size(SusuTheme.spacing.spacing_m))

        SusuBasicTextField(
            placeholder = "경조사명을 입력해주세요"
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF6F6F6)
@Composable
fun InputNameScreenPreview() {
    SusuTheme {
        InputNameScreen()
    }
}
