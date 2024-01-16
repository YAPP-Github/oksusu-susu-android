package com.susu.feature.mypage.main

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.susu.core.designsystem.theme.SusuTheme
import com.susu.core.ui.extension.collectWithLifecycle

@Composable
fun MyPageDefaultRoute(
    padding: PaddingValues,
    viewModel: MyPageViewModel = hiltViewModel(),
    navigateToLogin: () -> Unit,
) {
    viewModel.sideEffect.collectWithLifecycle { sideEffect ->
        when (sideEffect) {
            MyPageEffect.NavigateToLogin -> navigateToLogin()
            is MyPageEffect.ShowToast -> TODO()
        }
    }
}

@Composable
fun MyPageDefaultScreen(
    padding: PaddingValues,
    onLogoutClick: () -> Unit = {},
    onWithdrawClick: () -> Unit = {},
    navigateToLogin: () -> Unit = {},
) {
}

@Preview
@Composable
fun MyPageScreenPreview() {
    SusuTheme {
        MyPageDefaultScreen(padding = PaddingValues(0.dp))
    }
}
