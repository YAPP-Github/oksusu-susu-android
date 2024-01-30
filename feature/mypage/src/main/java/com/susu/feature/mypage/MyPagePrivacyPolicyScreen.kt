package com.susu.feature.mypage

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.susu.core.ui.PRIVACY_POLICY_URL

@Composable
fun MyPagePrivacyPolicyScreen(
    modifier: Modifier = Modifier,
) {
    AndroidView(
        modifier = modifier.fillMaxSize(),
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
