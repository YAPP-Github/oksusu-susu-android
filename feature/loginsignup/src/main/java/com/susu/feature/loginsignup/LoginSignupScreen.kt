package com.susu.feature.loginsignup

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.susu.core.designsystem.theme.SusuTheme

@Composable
fun LoginSignupScreen() {
    Text(
        text = "로그인 회원가입",
    )
}

@Preview
@Composable
fun SentScreenPreview() {
    SusuTheme {
        LoginSignupScreen()
    }
}
