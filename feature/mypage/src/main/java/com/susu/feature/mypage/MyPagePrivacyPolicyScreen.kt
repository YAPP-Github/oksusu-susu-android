package com.susu.feature.mypage

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.susu.core.designsystem.component.appbar.SusuDefaultAppBar
import com.susu.core.designsystem.component.appbar.icon.BackIcon
import com.susu.core.designsystem.theme.SusuTheme
import com.susu.core.ui.PRIVACY_POLICY_URL

@Composable
fun MyPagePrivacyPolicyScreen(
    modifier: Modifier = Modifier,
    popBackStack: () -> Unit,
) {
    Column(
        modifier.fillMaxSize(),
    ) {
        SusuDefaultAppBar(
            modifier = Modifier.padding(horizontal = SusuTheme.spacing.spacing_xs),
            leftIcon = {
                BackIcon(onClick = popBackStack)
            },
        )
        AndroidView(
            modifier = Modifier.weight(1f),
            factory = { context ->
                return@AndroidView WebView(context).apply {
                    webViewClient = WebViewClient()
                }
            },
            update = {
                it.loadUrl(PRIVACY_POLICY_URL)
            },
        )
    }
}
