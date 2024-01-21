package com.susu.feature.navigator

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.susu.core.designsystem.theme.SusuTheme
import com.susu.core.ui.INTENT_ACTION_DOWNLOAD_COMPLETE
import com.susu.core.ui.SnackbarToken
import com.susu.feature.loginsignup.social.KakaoLoginHelper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()
    private val uiState
        get() = viewModel.uiState.value

    private lateinit var broadcastReceiver: BroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        splashScreen.setKeepOnScreenCondition { uiState.showSplashScreen }

        if (uiState.isNavigating) {
            KakaoLoginHelper.getAccessToken { accessToken ->
                viewModel.navigate(
                    hasKakaoLoginHistory = KakaoLoginHelper.hasKakaoLoginHistory,
                    kakaoAccessToken = accessToken,
                )
            }
        }

        if (uiState.isInitializing) {
            viewModel.initCategoryConfig()
        }

        WindowCompat.setDecorFitsSystemWindows(window, false)

        broadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent?) {
                if (intent?.action == INTENT_ACTION_DOWNLOAD_COMPLETE) {
                    val id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1L)
                    if (id != -1L) {
                        viewModel.onShowSnackbar(SnackbarToken(message = context.getString(com.susu.feature.mypage.R.string.snackbar_success_export)))
                    }
                }
            }
        }

        ContextCompat.registerReceiver(
            this,
            broadcastReceiver,
            IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE),
            ContextCompat.RECEIVER_EXPORTED,
        )

        setContent {
            SusuTheme {
                MainScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .windowInsetsPadding(
                            WindowInsets.systemBars.only(
                                WindowInsetsSides.Vertical,
                            ),
                        ),
                    viewModel = viewModel,
                )
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(broadcastReceiver)
    }
}
