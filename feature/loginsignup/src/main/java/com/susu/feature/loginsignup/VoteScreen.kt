package com.susu.feature.loginsignup

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun VoteScreen(
    navigateToLogin: () -> Unit
) {
    Button(onClick = navigateToLogin) {
        Text("대충 회원가입 투표")
    }
}
